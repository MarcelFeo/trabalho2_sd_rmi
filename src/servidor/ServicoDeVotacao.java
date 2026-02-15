import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicoDeVotacao extends Remote {
    void votar(String eleitor, String candidato) throws RemoteException;
}