package clients;

import JpAws.Ec2ReservationService;
import api.Cluster;
import system.*;

public class PageRankEc2Client 
{
    public static void main( String[] args ) throws Exception
    {
        int numWorkers = Integer.parseInt( args[1] );        
        Job job = new Job( 
                "PageRank",
                args[0],    // jobDirectoryName, 
                new VertexPageRank(), 
                new MasterGraphMakerG1(),
                new WorkerGraphMakerStandard(),
                new MasterOutputMakerStandard(),
                new WorkerOutputMakerStandard()
                );
        System.out.println( job + "\n       numWorkers:" + numWorkers );
        Cluster cluster = Ec2ReservationService.newSmallCluster(numWorkers);
        cluster.run( job);    
        System.exit( 0 );
    }
}
