import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicoDeVotacao extends Remote {
    String votar(String eleitor, String candidato) throws RemoteException;

    boolean verificaVoto(String eleitor) throws RemoteException; // verifica se o eleitor já votou

    String[] getCandidatos() throws RemoteException;

}