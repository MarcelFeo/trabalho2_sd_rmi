# Visão Geral do Projeto

O objetivo é criar uma aplicação onde eleitores votam remotamente em candidatos através de uma interface RMI, com os resultados centralizados em um servidor.

## Requisitos do Sistema
- Java JDK instalado.
- RMI Registry para registro dos objetos remotos.
- Estrutura de dados para armazenamento de votos.
  
## Estrutura do Projeto

### 1. Servidor e Implementação
Responsáveis pela lógica de negócio e disponibilização dos serviços na rede.

* **Servidor.java**
    * Inicializa o servidor e cria o registro RMI

* **ServicoDeVotacaoImpl.java**
    * Armazena os votos em um hashmap e o registro de quem já votou em um set (para impedir votos duplicados)
    * Também implementa as interfaces ServicoDeVotacao e ServicoDeResultados

### 2. Interfaces
Definem os métodos que podem ser invocados remotamente pelos clientes.

* **ServicoDeVotacao.java**
    * Interface utilizada pelo cliente de votação

* **ServicoDeResultados.java**
    * Interface utilizada pelo cliente de apuração

### 3. Clientes
Aplicações de terminal para interação com o usuário final.

* **ClienteVotos.java**
    * Interface para o eleitor

* **ClienteResultado.java**
    * Interface para apuração, solicita os dados atuais e exibe uma tabela formatada no console contendo os candidatos e seus respectivos totais de votos.

---
### Pedro Souza Ferreira & Marcel Fernando Lobo de Feo