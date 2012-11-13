package clients;

import system.ClientToMaster;
import system.Job;
import system.LocalReservationService;
import system.MasterGraphMakerBinaryTree2;
import system.MasterOutputMakerStandard;
import system.WorkerGraphMakerBinaryTree2;
import system.WorkerOutputMakerStandard;
import vertices.VertexSsspBinaryTree;

/**
 *
 * @author Pete Cappello
 */
public class ClientSsspBinaryTreeLocal
{
    /**
     * @param args [0]: Job directory name
     */
    public static void main( String[] args ) throws Exception
    {
        int numWorkers = Integer.parseInt(args[1]);  
        Job job = new Job("Binary Tree Shortest Path",        // jobName
                args[0],                            // jobDirectoryName
                new VertexSsspBinaryTree(), // vertexFactory
                new MasterGraphMakerBinaryTree2(),  
                new WorkerGraphMakerBinaryTree2(),   
                new MasterOutputMakerStandard(),
                new WorkerOutputMakerStandard()                 
                );
        System.out.println( job + "\n    numWorkers: " + numWorkers );
        ClientToMaster master = LocalReservationService.newCluster(numWorkers);
        System.out.println(master.run(job));
        
        System.exit( 0 );
    }
}
