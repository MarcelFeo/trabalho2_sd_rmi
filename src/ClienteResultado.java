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
            System.out.println("=".repeat(70));
            System.out.println("SISTEMA DE CONSULTA DE RESULTADOS DA VOTAÇÃO");
            System.out.println("=".repeat(70));
            System.out.println();

            // Conecta ao registro RMI
            System.out.println("Conectando ao servidor...");
            Registry registry = LocateRegistry.getRegistry(HOST, PORTA_RMI);
            ServicoDeResultados servicoResultados =
                    (ServicoDeResultados) registry.lookup(NOME_SERVICO);
            System.out.println("[OK] Conectado ao servidor!\n");

            exibirResultadosCompletos(servicoResultados);


        } catch (RemoteException | NotBoundException e) {
            System.err.println("\n[ERRO] " + e.getMessage());
        }

    }

    private static void exibirResultadosCompletos(ServicoDeResultados servico)
            throws RemoteException {
        Map<String, Integer> resultados = servico.getResultados();

        System.out.println("<<< RESULTADOS DA VOTAÇÃO >>>");
        System.out.printf("%-30s | %-15s |", "CANDIDATO", "VOTOS");


        for (Map.Entry<String, Integer> entry : resultados.entrySet()) {
            String candidato = entry.getKey();
            int votos = entry.getValue();

            System.out.printf("%-30s | %-15d |", candidato, votos);
        }

    }
}
