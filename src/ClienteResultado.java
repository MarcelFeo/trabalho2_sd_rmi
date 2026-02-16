import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.Scanner;


public class ClienteResultado {

    private static final String HOST = "localhost";
    private static final int PORTA_RMI = 1099;
    private static final String NOME_SERVICO = "ServicoResultados";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("<<< RESULTADOS >>>\n");

            // Conecta ao registro RMI
            System.out.println("Conectando ao servidor...");
            Registry registry = LocateRegistry.getRegistry(HOST, PORTA_RMI);
            ServicoDeResultados servicoResultados =
                    (ServicoDeResultados) registry.lookup(NOME_SERVICO);
            System.out.println("[OK] Conectado ao servidor!\n");

            mostrarResultados(servicoResultados);


        } catch (RemoteException | NotBoundException e) {
            System.err.println("\n[ERRO] " + e.getMessage());
        }

    }

    private static void mostrarResultados(ServicoDeResultados servico)
            throws RemoteException {
        Map<String, Integer> resultados = servico.getResultados();

        System.out.printf("%-30s | %-15s |\n", "CANDIDATO", "VOTOS");


        for (Map.Entry<String, Integer> entry : resultados.entrySet()) {
            String candidato = entry.getKey();
            int votos = entry.getValue();

            System.out.printf("%-30s | %-15d |\n", candidato, votos);
        }

    }
}
