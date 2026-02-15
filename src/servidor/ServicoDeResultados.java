import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface ServicoDeResultados extends Remote {
    Map<String, Integer> getResultados() throws RemoteException;
}