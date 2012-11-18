package clients;

import JpAws.PregelAuthenticator;
import com.amazonaws.services.s3.AmazonS3Client;
import java.io.File;
import java.rmi.Naming;
import system.Job;
import system.JobRunData;
import system.Master;
import system.MasterGraphMakerBinaryTree2;
import system.MasterOutputMakerStandard;
import system.WorkerGraphMakerBinaryTree2;
import system.WorkerOutputMakerStandard;
import vertices.VertexSsspBinaryTree;

/**
 * A client that uses a preexisting AWS Cluster.
 * 
 * @author Pete Cappello
 */
public class ClientSsspBinaryTree
{
    /**
     * @param args[0] Public domain name of Master's machine
     *        args[1]: Job directory name
     *        args[2]: Number of workers
     *        args[3]: input file path name (path relative to project)
     */
    public static void main( String[] args ) throws Exception
    {
        int numWorkers = Integer.parseInt( args[2] );        
        System.out.println("ClientSsspBinaryTreeAws: numWorkers: " + numWorkers);
        new AmazonS3Client(PregelAuthenticator.get()).putObject( args[0], "input", new File( args[3] ) );
        Job job = new Job("Binary Tree Shortest Path",  // jobName
                args[1],
                new VertexSsspBinaryTree(),     // vertexFactory
                new MasterGraphMakerBinaryTree2(),  
                new WorkerGraphMakerBinaryTree2(),   
                new MasterOutputMakerStandard(),
                new WorkerOutputMakerStandard()                 
                );
        String url = "rmi://" + args[0] + "/" + Master.CLIENT_SERVICE_NAME;
        Master master = (Master) Naming.lookup( url );
        JobRunData jobRunData = master.run( job );
        System.out.println( jobRunData );
        System.exit( 0 );
    }
}
