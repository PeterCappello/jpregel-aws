package clients;

import api.ClusterImpl;

/**
 *
 * @author Pete Cappello
 */
public class ClusterTerminate
{
    /**
     * Terminate an EC2 cluster.
     */
    public static void main( String[] args ) throws Exception
    {
        ClusterImpl.getCluster().terminate();
        System.out.println("ClusterImpl.main: Cluster terminated.");
        System.exit( 0 );
    }
}
