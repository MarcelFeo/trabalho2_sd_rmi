import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServicoDeVotacaoImpl extends UnicastRemoteObject implements ServicoDeVotacao, ServicoDeResultados {
    //nome do cndidato > quantidade de votos
    private final Map<String, Integer> mapaDeVotos; // nome candidato e qtd de votos
    private final Set<String> eleitoresQueVotaram; // eleitores que já votaram

    private ServicoDeVotacaoImpl() throws RemoteException {
        super();
        this.mapaDeVotos = new HashMap<>();
        this.eleitoresQueVotaram = ConcurrentHashMap.newKeySet();
    }

    @Override
    public synchronized void votar(String eleitor, String candidato) throws RemoteException {

        try {
            if (eleitor == null || eleitor.trim().isEmpty()) {
                throw new IllegalArgumentException("-> O eleitor não pode ser vazio :(");
            }
            if (candidato == null || candidato.trim().isEmpty()) {
                throw new IllegalArgumentException("-> O candidato não pode ser vazio :(");
            }
            if (eleitoresQueVotaram.contains(eleitor.trim())) {
                throw new IllegalArgumentException("-> Eleitor: " + eleitor + " já votou :)");
            }
            if (!mapaDeVotos.containsKey(candidato)) {
                throw new IllegalArgumentException("-> Candidato: " + candidato + " não existe :(");
            }

            mapaDeVotos.put(candidato, mapaDeVotos.get(candidato) + 1);
            eleitoresQueVotaram.add(eleitor.trim());

            String mensagem = "-> VOTO REGISTRADO\n-> Eleitor: " + eleitor +
                    "\n -> Candidato: " + candidato;
            System.out.println("<VOTO>\n " + mensagem);

        } catch (IllegalArgumentException e) {
            System.err.println("[ERRO] " + e.getMessage());
            throw new RemoteException(e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("[ERRO] Erro inesperado ao processar voto: " + e.getMessage());
            throw new RemoteException("Erro ao processar voto: " + e.getMessage(), e);
        }

    }

    public Map<String, Integer> getResultados() throws RemoteException {
        //(sincronizado p evitar leitura enquanto alguem esta escrevendo[
        synchronized (this) {
            return new HashMap<>(mapaDeVotos);
        }
    }
}