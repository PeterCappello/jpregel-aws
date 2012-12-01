package clients;

import JpAws.Ec2ReservationService;

/**
 * Launch an EC2 cluster.
 * @author Pete Cappello
 */
public class ClusterLaunchEc2
{
    /**
     * Launch an EC2 cluster.
     * @param args[0]: Number of workers
     */
    public static void main( String[] args ) throws Exception
    {
        int numWorkers = Integer.parseInt( args[0] );  
        System.out.println( "ClusterLaunchEc2.main: Launching " + numWorkers + " workers." );
        Ec2ReservationService.newMassiveCluster( numWorkers );
    }
}
