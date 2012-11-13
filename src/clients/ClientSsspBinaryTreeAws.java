package clients;

import system.ClientToMaster;
import system.Job;
import system.JobRunData;
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
public class ClientSsspBinaryTreeAws
{
    /**
     * @param args[0]: Job directory name
     *        args[1]: Number of workers
     *        args[2]: File name (path relative to project)
     */
    public static void main( String[] args ) throws Exception
    {
        int numWorkers = Integer.parseInt( args[1] );  
        System.out.println("ClientSsspBinaryTreeAws: numWorkers: " + numWorkers);
//        Cluster master = Ec2ReservationService.newMassiveCluster(numWorkers);
        ClientToMaster master = LocalReservationService.newCluster(numWorkers);
//        if( args.length > 2 ) 
//        {
//            new AmazonS3Client(PregelAuthenticator.get()).putObject( args[0], "input", new File( args[2] ) );
//        }
        Job job = new Job("Binary Tree Shortest Path",  // jobName
                "examples/SsspBinaryTree", //args[0],              // jobDirectoryName (S3 bucket name)
                new VertexSsspBinaryTree(),     // vertexFactory
                new MasterGraphMakerBinaryTree2(),  
                new WorkerGraphMakerBinaryTree2(),   
                new MasterOutputMakerStandard(),
                new WorkerOutputMakerStandard()                 
                );
        JobRunData jobRunData = master.run( job );
        System.out.println( jobRunData );
//        master.terminate();
        System.exit( 0 );
    }
}
