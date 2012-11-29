package JpAws;

import api.Cluster;
import api.ClusterImpl;
import java.io.IOException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author charlesmunger
 */
public class Ec2Cluster extends ClusterImpl 
{    
    public static void main( String... args ) throws RemoteException, AlreadyBoundException, ExecutionException, IOException, InterruptedException 
    {
        System.setSecurityManager(new RMISecurityManager());
        Registry registry = LocateRegistry.createRegistry( Cluster.PORT );
        ClusterImpl master = new Ec2Cluster( args );
        registry.bind( Cluster.SERVICE_NAME, master );
        System.out.println( "Ec2Master: Ready." );
    }
    
    private Ec2Cluster( String... args ) throws RemoteException, ExecutionException, IOException, InterruptedException
    {
        super(new Ec2ReservationService(), args[0], args[1], Integer.valueOf(args[2]));
    }
}
