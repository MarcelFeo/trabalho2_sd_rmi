### Visão Geral do Projeto

O objetivo é criar uma aplicação onde eleitores votam remotamente em candidatos através de uma interface RMI, com os resultados centralizados em um servidor.
+1

Requisitos do Sistema
Java JDK instalado.


RMI Registry para registro dos objetos remotos.

Estrutura de dados para armazenamento de votos.

### Guia de Desenvolvimento Passo a Passo
1. Definição das Interfaces Remotas
   Crie as interfaces que estendem java.rmi.Remote. Elas definem os métodos que podem ser chamados pelos clientes.
   


ServicoDeVotacao: Deve conter o método void votar(String eleitor, String candidato).


ServicoDeResultados: Deve conter um método que retorne um Map<String, Integer> com o total de votos por candidato.


2. Implementação da Lógica (ServicoDeVotacaoImpl)
   Crie a classe que implementa ambas as interfaces acima.

Utilize uma estrutura de dados (como HashMap) para rastrear os votos.

Implemente o tratamento de exceções, especificamente RemoteException.

3. Configuração do Servidor RMI
   Desenvolva a classe servidora responsável por:

Instanciar o objeto remoto ServicoDeVotacaoImpl.

Registrar o objeto no Naming ou Registry do RMI para que os clientes o encontrem.

4. Cliente de Votação
   Implemente o cliente que interage com o eleitor. O fluxo deve ser:

Solicitar o nome do eleitor.

Exibir a lista de candidatos disponíveis.

Capturar a escolha do eleitor.

Invocar o método votar remotamente.

Exibir a confirmação do voto recebida do servidor.

5. Cliente de Consulta (Resultados)
   Crie um cliente adicional focado em monitoramento.
   

Ele deve chamar os métodos da interface ServicoDeResultados para exibir o placar atualizado da votação.

### Informações de Entrega

Data Limite: 18/02/2026.

Formato: Individual ou em dupla.

Plataforma: Todos os arquivos devem ser enviados via Microsoft Teams.

Gostaria que eu gerasse um exemplo de código inicial para a interface ServicoDeVotacao seguindo essas especificações?