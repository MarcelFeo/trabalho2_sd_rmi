import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServicoDeVotacaoImpl servicoVotacao = new ServicoDeVotacaoImpl();
            Registry registro = LocateRegistry.createRegistry(1099);
            registro.rebind("VotacaoService", servicoVotacao);
            System.out.println("servidor de votacao esta online");
        } catch (Exception e) {
            System.err.println("erro no servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}