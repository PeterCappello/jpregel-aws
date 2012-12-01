package clients;

import JpAws.Ec2ReservationService;
import JpAws.PregelAuthenticator;
import api.Cluster;
import com.amazonaws.services.s3.AmazonS3Client;
import java.io.File;
import system.Job;
import system.JobRunData;
import system.MasterGraphMakerBinaryTree2;
import system.MasterOutputMakerStandard;
import system.WorkerGraphMakerBinaryTree2;
import system.WorkerOutputMakerStandard;
import vertices.VertexSsspBinaryTree;

/**
 * @author Pete Cappello
 */
public class CompleteSsspBinaryTreeAws
{
    /**
     * @param args[0]: Job directory name
     *        args[1]: Number of workers
     *        args[2]: local file path name (relative to project)
     */
    public static void main( String[] args ) throws Exception
    {
        int numWorkers = Integer.parseInt( args[1] );  
        System.out.println("ClientSsspBinaryTreeAws: numWorkers: " + numWorkers);
        Cluster cluster = Ec2ReservationService.newMassiveCluster(numWorkers);
        new AmazonS3Client(PregelAuthenticator.get()).putObject( args[0], "input", new File( args[2] ) );
        Job job = new Job("Binary Tree Shortest Path",  // jobName
                args[0],              // jobDirectoryName (S3 bucket name)
                new VertexSsspBinaryTree(),     // vertexFactory
                new MasterGraphMakerBinaryTree2(),  
                new WorkerGraphMakerBinaryTree2(),   
                new MasterOutputMakerStandard(),
                new WorkerOutputMakerStandard()                 
                );
        JobRunData jobRunData = cluster.run( job );
        System.out.println( jobRunData );
        cluster.terminate();
        System.exit( 0 );
    }
}
