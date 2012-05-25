package system;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Pete Cappello
 */
public interface ClientToMaster extends Remote
{
    void makeWorkers( int numWorkers, String masterDomainName, String imageIdWorker ) throws RemoteException;
    
    JobRunData run( Job job, boolean isEc2 ) throws RemoteException;
    
    void setWorkerMap() throws RemoteException;
    
    void shutdown() throws RemoteException;
}
