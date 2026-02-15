import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ServicoDeVotacaoImpl extends UnicastRemoteObject implements ServicoDeVotacao,ServicoDeResultados{
    //nome do cndidato > quantidade de votos
    private Map<String, Integer> mapaDeVotos;
    //talvez seja legal implementar outra estrutura pra contar quem
    // ja votou e evitar votos duplos n sei 
    protected ServicoDeVotacaoImpl() throws RemoteException {
        super();
        this.mapaDeVotos = new HashMap<>();
    }
    public void votar(String eleitor, String candidato) throws RemoteException {
        synchronized (this) {
            int votosAtuais = mapaDeVotos.getOrDefault(candidato,0);
            mapaDeVotos.put(candidato,votosAtuais+1);
        }
        System.out.println("voto registrado: eleitor '" + eleitor + "' votou em '" + candidato + "'");
    }

    public Map<String, Integer> getResultados() throws RemoteException {
        //(sincronizado p evitar leitura enquanto alguem esta escrevendo[
        synchronized (this) {
            return new HashMap<>(mapaDeVotos);
        }
    }
}