package vertices;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import system.Message;
import system.VertexImpl;
import system.VertexShortestPath;

/**
 *
 * @author Pete Cappello
 */
public class VertexSsspBinaryTree extends VertexShortestPath
{
    public VertexSsspBinaryTree() {}
    
    public VertexSsspBinaryTree( Integer vertexId, Message<Integer, Integer> vertexValue, Map<Integer, Integer> edgeMap )
    { 
        super( vertexId, edgeMap ,2);
        setVertexValue( new Message( vertexId, Integer.MAX_VALUE ) );
    }
    
    @Override
    public VertexImpl make( String line )
    {
        StringTokenizer stringTokenizer = new StringTokenizer( line );
        Integer vertexId = Integer.parseInt( stringTokenizer.nextToken() );
        int numChildren  = Integer.parseInt( stringTokenizer.nextToken() );
        return make( vertexId, numChildren);
    }
    
    public VertexImpl make( Integer vertexId, int numChildren )
    {
        Integer initialKnownDistance = ( vertexId == 1 ) ? 0 : Integer.MAX_VALUE;
        Message<Integer, Integer> vertexValue = new Message<Integer, Integer>( 1, initialKnownDistance );
        Map<Integer, Integer> edgeMap = new HashMap<Integer, Integer>( numChildren );
        switch ( numChildren )
        {
            case 2: edgeMap.put( 2 * vertexId + 1, 1 );
            case 1: edgeMap.put( 2 * vertexId,     1 );
            default: break; // no children
        }
        return new VertexShortestPathBinaryTree( vertexId, vertexValue, edgeMap );
    }
    
    /*
     * Assumption: numParts is a power of 2.
     */
    @Override
    public int getPartId( Integer vertexId, int numParts )
    {
        int shiftAmount = Integer.numberOfLeadingZeros( numParts ) - Integer.numberOfLeadingZeros( vertexId );
        int partId = ( shiftAmount >= 0 ) ? vertexId >> shiftAmount : vertexId << -shiftAmount;
        return partId - numParts;
    }
    
    @Override
    public int getWorkerNum( int partId, int numWorkers ) { return partId / ( 2 * availableProcessors ) + 1; }
}
