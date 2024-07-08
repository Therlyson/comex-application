package therlyson;

import therlyson.model.Cliente;
import therlyson.service.ClienteService;
import therlyson.utils.JPAutil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class MainCliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManager manager = JPAutil.getEntityManager();
        ClienteService clienteService = new ClienteService(manager);
        boolean running = true;

        while (running) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente");
            System.out.println("4. Atualizar cliente");
            System.out.println("5. Deletar cliente");
            System.out.println("6. Listar clientes por nome");
            System.out.println("7. sair");
            System.out.print("Opção: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.print("CEP: ");
                    String cep = scanner.nextLine();

                    Cliente cliente = new Cliente(cpf, nome, email, telefone, cep);
                    clienteService.salvarCliente(cliente);
                    break;

                case 2:
                    List<Cliente> clientes = clienteService.listarTodosOsClientes();
                    if (clientes.isEmpty()) {
                        System.out.println("Não existe clientes no banco!");
                    } else {
                        System.out.println("\nTodos os clientes casdastrados no banco de dados: ");
                        for (Cliente c : clientes) {
                            System.out.println(c);
                        }
                    }
                    break;

                case 3:
                    System.out.print("\nID do cliente a buscar: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    Cliente clienteBuscado = clienteService.pesquisarClientePorId(id);
                    if (clienteBuscado == null) {
                        System.out.println("Cliente não encontrado");
                    } else {
                        System.out.println(clienteBuscado);
                    }
                    break;

                case 4:
                    System.out.print("\nID do cliente a atualizar: ");
                    Long idAtualizar = scanner.nextLong();
                    scanner.nextLine();

                    Cliente clienteParaAtualizar = clienteService.pesquisarClientePorId(idAtualizar);
                    if (clienteParaAtualizar != null) {
                        System.out.print("Novo CPF (atual: " + clienteParaAtualizar.getCpf() + "): ");
                        clienteParaAtualizar.setCpf(scanner.nextLine());
                        System.out.print("Novo Nome (atual: " + clienteParaAtualizar.getNome() + "): ");
                        clienteParaAtualizar.setNome(scanner.nextLine());
                        System.out.print("Novo Email (atual: " + clienteParaAtualizar.getEmail() + "): ");
                        clienteParaAtualizar.setEmail(scanner.nextLine());
                        System.out.print("Novo Telefone (atual: " + clienteParaAtualizar.getTelefone() + "): ");
                        clienteParaAtualizar.setTelefone(scanner.nextLine());
                        System.out.print("Novo CEP (atual: " + clienteParaAtualizar.getCep() + "): ");
                        clienteParaAtualizar.setCep(scanner.nextLine());

                        Cliente clienteAtualizado = clienteService.atualizarCliente(clienteParaAtualizar);
                        System.out.println("\nCliente atualizado: " + clienteAtualizado);
                    } else {
                        System.out.println("Cliente não encontrado");
                    }
                    break;

                case 5:
                    System.out.print("\nID do cliente a deletar: ");
                    Long idDeletar = scanner.nextLong();
                    scanner.nextLine();
                    clienteService.removerCLiente(idDeletar);
                    System.out.println("Cliente removido com sucesso!");
                    break;

                case 6:
                    List<String> clientesPorNome = clienteService.listarClientesPorNome();
                    if (clientesPorNome.isEmpty()) {
                        System.out.println("\nNão existe clientes no banco!");
                    } else {
                        System.out.println("\nTodos os nomes de clientes no banco de dados: ");
                        for (String c : clientesPorNome) {
                            System.out.println(" - " + c);
                        }
                    }
                    break;

                case 7:
                    System.out.println("Saindo...");
                    scanner.close();
                    manager.close();
                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
            System.out.println();
        }
    }
}
