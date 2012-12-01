package clients;

import api.ClusterImpl;

/**
 * Terminate an EC2 cluster.
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
        System.out.println("ClusterTerminate.main: Cluster terminated.");
    }
}
