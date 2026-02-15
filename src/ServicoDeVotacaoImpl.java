import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServicoDeVotacaoImpl extends UnicastRemoteObject implements ServicoDeVotacao, ServicoDeResultados {
    //nome do cndidato > quantidade de votos
    private final Map<String, Integer> mapaDeVotos; // nome candidato e qtd de votos
    private final Set<String> registroEleitores; // eleitores que já votaram
    private String[] candidatos;

    ServicoDeVotacaoImpl() throws RemoteException {
        super();
        this.mapaDeVotos = new HashMap<>();
        this.registroEleitores = ConcurrentHashMap.newKeySet();

        // lista de candidatos
        this.candidatos = new String[]{
                "Candidato 01",
                "Candidato 02",
                "Candidato 03",
                "Candidato 04"
        };

        // mapa de votos com zero votos para cada candidato
        for (String candidato : candidatos) {
            mapaDeVotos.put(candidato, 0);
        }
    }

    @Override
    public synchronized String votar(String eleitor, String candidato) throws RemoteException {

        try {
            if (eleitor == null || eleitor.trim().isEmpty()) {
                throw new IllegalArgumentException("-> O eleitor não pode ser vazio :(");
            }
            if (candidato == null || candidato.trim().isEmpty()) {
                throw new IllegalArgumentException("-> O candidato não pode ser vazio :(");
            }
            if (registroEleitores.contains(eleitor.trim())) {
                throw new IllegalArgumentException("-> Eleitor: " + eleitor + " já votou :)");
            }
            if (!mapaDeVotos.containsKey(candidato)) {
                throw new IllegalArgumentException("-> Candidato: " + candidato + " não existe :(");
            }

            mapaDeVotos.put(candidato, mapaDeVotos.get(candidato) + 1);
            registroEleitores.add(eleitor.trim());

            String mensagem = "-> VOTO REGISTRADO\n-> Eleitor: " + eleitor +
                    "\n -> Candidato: " + candidato;
            System.out.println("<VOTO>\n " + mensagem);

            return mensagem;

        } catch (Exception e) {
            System.err.println("[ERRO] Erro inesperado ao processar voto: " + e.getMessage());
            throw new RemoteException("Erro ao processar voto: " + e.getMessage(), e);
        }

    }

    @Override
    public boolean verificaVoto(String eleitor) throws RemoteException {

        try {
            if (eleitor == null || eleitor.trim().isEmpty()) {
                return false;
            }
            return registroEleitores.contains(eleitor.trim());
        } catch (Exception e) {
            System.err.println("[ERRO]: " + e.getMessage());
            throw new RemoteException("[ERRO]: ", e);
        }

    }

    @Override
    public String[] getCandidatos() throws RemoteException {

        try {
            return candidatos.clone();
        } catch (Exception e) {
            System.err.println("[ERRO]: " + e.getMessage());
            throw new RemoteException("[ERRO]: ", e);
        }

    }

    @Override
    public Map<String, Integer> getResultados() throws RemoteException {
        synchronized (this) {
            return new HashMap<>(mapaDeVotos);
        }
    }
}