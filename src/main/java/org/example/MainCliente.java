package org.example;

import org.example.model.Cliente;
import org.example.services.ClienteService;
import org.example.utils.JPAutil;

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
            System.out.println("Escolha uma opção:");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente");
            System.out.println("4. Atualizar cliente");
            System.out.println("5. Deletar cliente");
            System.out.println("6. Sair");
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
                    if(clientes.isEmpty()){
                        System.out.println("Não existe clientes no banco!");
                        return;
                    }
                    for (Cliente c : clientes) {
                        System.out.println(c);
                    }
                    break;

                case 3:
                    System.out.print("ID do cliente a buscar: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    Cliente clienteBuscado = clienteService.pesquisarClientePorId(id);
                    if (clienteBuscado == null) {
                        System.out.println("Cliente não encontrado");
                        return;
                    }
                    System.out.println(clienteBuscado);
                    break;

                case 4:
                    System.out.print("ID do cliente a atualizar: ");
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
                        System.out.println("Cliente atualizado: " + clienteAtualizado);
                    } else {
                        System.out.println("Cliente não encontrado");
                    }
                    break;

                case 5:
                    System.out.print("ID do cliente a deletar: ");
                    Long idDeletar = scanner.nextLong();
                    scanner.nextLine();
                    clienteService.removerCLiente(idDeletar);
                    System.out.println("Cliente removido com sucesso!");
                    break;
                case 6:
                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
            System.out.println();
        }

        scanner.close();
        manager.close();
        System.out.println("Programa encerrado.");
    }
}
