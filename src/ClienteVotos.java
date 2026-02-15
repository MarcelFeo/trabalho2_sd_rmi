import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClienteVotos {

    private static final String HOST = "localhost";
    private static final int PORTA_RMI = 1099;
    private static final String NOME_SERVICO = "ServicoVotacao";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServicoDeVotacao servicoVotacao = null;

        try {
            System.out.println("< SISTEMA DE VOTAÇÃO >\n");

            // Conecta ao registro RMI
            System.out.println("Conectando ao servidor de votação...");
            Registry registry = LocateRegistry.getRegistry(HOST, PORTA_RMI);
            servicoVotacao = (ServicoDeVotacao) registry.lookup(NOME_SERVICO);
            System.out.println("[OK] Conectado :)\n");

            // Solicita o nome do eleitor
            System.out.print("-> Nome: ");
            String eleitor = scanner.nextLine().trim();

            if (eleitor.isEmpty()) {
                System.err.println("[ERRO] o campo 'Nome' não pode ser vazio...");
                return;
            }

            // Vverifica se o eleitor já votou
            if (servicoVotacao.verificaVoto(eleitor)) {
                System.err.println("\nATENÇÃO!!! " + eleitor + "você já votou...");
                return;
            }

            System.out.println("\nBem vindo, " + eleitor + "!");

            // exibe a lista de candidatos
            String[] candidatos = servicoVotacao.getCandidatos();

            System.out.println("-> Todos candidatos....\n");

            for (int i = 0; i < candidatos.length; i++) {
                System.out.printf("<%d>. %s%n", (i + 1), candidatos[i]);
            }

            // Solicita o voto do eleitor
            int opcao = 0;
            boolean entradaValida = false;

            while (!entradaValida) {
                try {
                    System.out.print("-> Qual candidato vc vai votar? ");
                    String input = scanner.nextLine().trim();
                    opcao = Integer.parseInt(input);

                    if (opcao < 1 || opcao > candidatos.length) {
                        System.err.println("[ERRO] Somente opções entre 1 e " +
                                candidatos.length + "são válidas...");
                    } else {
                        entradaValida = true;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("[ERRO] digite um número válido!");
                }
            }

            String candidatoEscolhido = candidatos[opcao - 1];

            // registra o voto no servidor
            System.out.println("\n-> Registrando seu voto...");
            String resultado = servicoVotacao.votar(eleitor, candidatoEscolhido);

            // Exibe confirmação
            System.out.println("\n<<< VOTO CONFIRMADO >>>\n");
            System.out.println(resultado);

        } catch (RemoteException e) {
            System.err.println("\n[ERRO] " + e.getMessage());
        }

    }
}