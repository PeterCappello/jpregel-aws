package system.commands;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import jicosfoundation.Command;
import jicosfoundation.Proxy;
import system.Worker;

/**
 *
 * @author Pete appello
 */
public class VertexIdToMessageQMapReceived implements Command<Worker>
{
    @Override
    public void execute(Proxy proxy) { proxy.sendCommand( this ); }

    @Override
    public void execute(Worker worker) { /*worker.vertexIdToMessageQMapReceived();*/ }
    
    @Override
    public void writeExternal(ObjectOutput oo) throws IOException
    {
//        oo.write(workerNum);
    }

    @Override
    public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException
    {
//        workerNum = oi.readInt();
    }
}
