package JpLAN;

import api.ClusterImpl;
import api.MachineGroup;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import system.ClientToMaster;
import system.ReservationServiceImpl;
import system.Worker;

/**
 *
 * @author charlesmunger
 */
public class LANReservationService extends ReservationServiceImpl {

    @Override
    public MachineGroup<Worker> callWorker(String instanceType, int numberOfWorkers)
    {
        return new LANWorkerMachineGroup(numberOfWorkers);
    }

    @Override
    public MachineGroup<ClientToMaster> callMaster(String instanceType)
    {
        return new LANMasterMachineGroup();
    }
    
    public static ClusterImpl newLocalCluster(int numWorkers) throws InterruptedException, ExecutionException, IOException {
        LANReservationService rs = new LANReservationService();
        return new ClusterImpl (rs,"","",numWorkers);
    }
}
