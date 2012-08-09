package system;
 
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Peter Cappello
 */
public final class Part
{
    private final int partId;
    private final Job job;
    
    // TODO Part: vertexIdToVertexMap: What is a good initial capacity? 
    private Map<Object, Vertex> vertexIdToVertexMap = new ConcurrentHashMap<Object, Vertex>( 8000 , 0.9f, 2);
    private OntoMap<Set<Vertex>> superstepToActiveSetMap = new OntoMap<Set<Vertex>>( new ActiveSet() );
    
    // superStep parameters
    private ComputeThread computeThread;
    private long superStep;
    private ComputeInput computeInput;
    
    // superStep parameters modified by each vertex.compute()
    private Aggregator outputProblemAggregator;
    private Aggregator outputStepAggregator;
    private int numMessagesSent; 
    
    Part( int partId, Job job )
    {
        this.partId = partId;
        this.job    = job;
    }
    
    /*
     * FIX: For graph mutation (add vertex), also need void addVertexToActiveSet( Long superStep, Vertex vertex )
     */
    void add( Vertex vertex )
    {
        vertex.setPart( this );
        vertexIdToVertexMap.put( vertex.getVertexId(), vertex );
        if ( vertex.isSource() )
        {
            Set<Vertex> activeSet = superstepToActiveSetMap.get( new Long(0) );
            activeSet.add( vertex );
        }
    }
    
    void addToActiveSet( long superStep, Vertex vertex ) { superstepToActiveSetMap.get( superStep ).add( vertex ); }
    
    void aggregateOutputProblemAggregator( Aggregator aggregator ) { outputProblemAggregator.aggregate(aggregator); }
    
    void aggregateOutputStepAggregator( Aggregator aggregator ) { outputStepAggregator.aggregate(aggregator); }
    
    ComputeOutput doSuperStep( ComputeThread computeThread, long superStep, ComputeInput computeInput )
    {
        this.computeThread = computeThread;
        this.superStep = superStep;
        this.computeInput = computeInput;
        numMessagesSent   = 0;
        outputStepAggregator    = job.makeStepAggregator();
        outputProblemAggregator = job.makeProblemAggregator();
        Set<Vertex> activeSet = superstepToActiveSetMap.get( superStep );
        for ( Vertex vertex : activeSet )
        {
            vertex.compute();
            vertex.removeMessageQ( superStep );      // MessageQ now is garbage
        }
        superstepToActiveSetMap.remove( superStep ); // active vertex set now is garbage
        boolean thereIsNextStep = numMessagesSent > 0;
        return new ComputeOutput( thereIsNextStep, outputStepAggregator, outputProblemAggregator );
    }
        
    ComputeInput getComputeInput() { return computeInput; }
        
    int getPartId() { return partId; }
    
    long getSuperStep() { return superStep; }
    
    Vertex getVertex( int vertexId ) { return vertexIdToVertexMap.get( vertexId ); }
    
    Map<Object, Vertex> getVertexIdToVertexMap() { return vertexIdToVertexMap; }
    
    public Collection<Vertex> getVertices() { return vertexIdToVertexMap.values(); }
    
    void incrementNumMessagesSent() { numMessagesSent++; }
    
    synchronized void receiveMessage( Object vertexId, Message message, long superStep )
    {
        Vertex vertex = vertexIdToVertexMap.get( vertexId );
        // BEGIN DEBUG
        if ( vertex == null )
        {
            System.out.println("Part.receiveMessage: vertexId: " + vertexId );
        }
        // END DEBUG
        vertex.receiveMessage( message, superStep );
        addToActiveSet( superStep, vertex );
    }
    
    synchronized void receiveMessageQ( Object vertexId, MessageQ messageQ, long superStep )
    {
        Vertex vertex = vertexIdToVertexMap.get( vertexId );
        vertex.receiveMessageQ( messageQ, superStep );
        addToActiveSet( superStep, vertex );
    }
        
    void removeFromActiveSet( long superStep, Vertex vertex )
    {
        superstepToActiveSetMap.get( superStep ).remove( vertex );
    }
    
    void removeVertex( Object vertexId )
    {
        vertexIdToVertexMap.remove( vertexId );
        computeThread.removeVertex();
    }
    
    void sendMessage( Object vertexId, Message message, long superStep )
    {
        numMessagesSent++;
        int receivingPartId = job.getPartId( vertexId );
        if ( receivingPartId == this.partId )
        {
            receiveMessage( vertexId, message, superStep );
        }
        else
        {
            computeThread.sendMessage( receivingPartId, vertexId, message, superStep );
        }
    }
}
