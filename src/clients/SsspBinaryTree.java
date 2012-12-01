package clients;

import JpAws.PregelAuthenticator;
import api.ClusterImpl;
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
 * A client that uses a preexisting AWS Cluster.
 * 
 * @author Pete Cappello
 */
public class SsspBinaryTree
{
    /**
     * @param args[0]: cloud job directory name
     *        args[1]: local job directory name (path name relative to project) containing input file
     */
    public static void main( String[] args ) throws Exception
    {
        new AmazonS3Client(PregelAuthenticator.get()).putObject( "petercappello", "input", new File( args[1] ) );
        Job job = new Job("Binary Tree Shortest Path",  // jobName
                args[0],
                new VertexSsspBinaryTree(),     // vertexFactory
                new MasterGraphMakerBinaryTree2(),  
                new WorkerGraphMakerBinaryTree2(),   
                new MasterOutputMakerStandard(),
                new WorkerOutputMakerStandard()                 
                );
        System.out.println("SsspBinaryTree: " + job);
        JobRunData jobRunData = ClusterImpl.getCluster().run( job, args[1] );
        System.out.println( jobRunData );
        System.exit( 0 );
    }
}
