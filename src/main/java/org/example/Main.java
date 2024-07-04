package org.example;

import org.example.model.Categoria;
import org.example.model.Cliente;
import org.example.model.Produto;
import org.example.services.CategoriaService;
import org.example.services.ClienteService;
import org.example.services.ProdutoService;
import org.example.utils.JPAutil;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManager manager = JPAutil.getEntityManager();
        boolean running = true;

        while (running) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Operações com Clientes");
            System.out.println("2. Operações com Categorias");
            System.out.println("3. Operações com Produtos");
            System.out.println("4. Sair");
            System.out.print("Opção: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    operacoesCliente(scanner, manager);
                    break;

                case 2:
                    operacoesCategoria(scanner, manager);
                    break;

                case 3:
                    operacoesProduto(scanner, manager);
                    break;

                case 4:
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


    private static void operacoesCliente(Scanner scanner, EntityManager manager) {
        ClienteService clienteService = new ClienteService(manager);
        boolean running = true;

        while (running) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente");
            System.out.println("4. Atualizar cliente");
            System.out.println("5. Deletar cliente");
            System.out.println("6. Voltar ao menu principal");
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
                        for (Cliente c : clientes) {
                            System.out.println(c);
                        }
                    }
                    break;

                case 3:
                    System.out.print("ID do cliente a buscar: ");
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
    }

    private static void operacoesCategoria(Scanner scanner, EntityManager em) {
        CategoriaService categoriaService = new CategoriaService(em);
        boolean running = true;

        while (running) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Cadastrar categoria");
            System.out.println("2. Listar categorias");
            System.out.println("3. Buscar categorias por nome ordenado");
            System.out.println("4. Atualizar categoria");
            System.out.println("5. Deletar categoria");
            System.out.println("6. Voltar ao menu principal");
            System.out.print("Opção: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();

                    Categoria categoria = new Categoria(nome, descricao);
                    categoriaService.cadastrarCategoria(categoria);
                    break;

                case 2:
                    List<Categoria> categorias = categoriaService.listarCategorias();
                    for (Categoria c : categorias) {
                        System.out.println(c);
                    }
                    break;

                case 3:
                    List<String> categoriaNome =  categoriaService.consultarCategoriasPorNome();
                    for(String c: categoriaNome){
                        System.out.println(c);
                    }
                    break;

                case 4:
                    System.out.print("ID da categoria a atualizar: ");
                    Long idAtualizar = scanner.nextLong();
                    scanner.nextLine();

                    Categoria categoriaParaAtualizar = categoriaService.buscarCategoriaId(idAtualizar);
                    if (categoriaParaAtualizar != null) {
                        System.out.print("Novo Nome (atual: " + categoriaParaAtualizar.getNome() + "): ");
                        categoriaParaAtualizar.setNome(scanner.nextLine());
                        System.out.print("Nova Descrição (atual: " + categoriaParaAtualizar.getDescricao() + "): ");
                        categoriaParaAtualizar.setDescricao(scanner.nextLine());

                        Categoria categoriaAtualizada = categoriaService.alterarCategoria(categoriaParaAtualizar);
                        System.out.println("Categoria atualizada: " + categoriaAtualizada);
                    } else {
                        System.out.println("Categoria não encontrada");
                    }
                    break;

                case 5:
                    System.out.print("ID da categoria a deletar: ");
                    Long idDeletar = scanner.nextLong();
                    boolean deletar = true;

                    ProdutoService ps = new ProdutoService(em);
                    List<Produto> produtos = ps.listarProdutos();
                    for (Produto produto : produtos) {
                        for(Categoria categoriateste: produto.getCategoria()){
                            if(Objects.equals(categoriateste.getId(), idDeletar)){
                                System.out.println("Impossivel remover categoria, remova primeiro o produto");
                                deletar = false;
                            }
                        }
                    }
                    scanner.nextLine();
                    if(deletar){
                        categoriaService.removerCategoria(idDeletar);
                        System.out.println("Categoria removida com sucesso!");
                    }
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
    }

    private static void operacoesProduto(Scanner scanner, EntityManager manager) {
        CategoriaService categoriaService = new CategoriaService(manager);
        ProdutoService produtoService = new ProdutoService(manager);
        boolean running = true;

        while (running) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Consultar produtos por ordem de nome");
            System.out.println("4. Alterar produto");
            System.out.println("5. Remover Produto");
            System.out.println("6. Voltar ao menu principal");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do produto: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite a descrição do produto: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Digite o preço do produto: ");
                    double preco = scanner.nextDouble();

                    boolean cadastrar = true;
                    List<Categoria> categoriasEscolhidas = new ArrayList<>();
                    while (cadastrar) {
                        List<Categoria> categoriasExistentes = categoriaService.listarCategorias();
                        if (categoriasExistentes.isEmpty()) {
                            System.out.println("Não existem categorias cadastradas no momento, impossível cadastrar um produto!");
                            break;
                        }
                        System.out.println("\nLista de categorias disponíveis:");
                        for (Categoria c : categoriasExistentes) {
                            System.out.println(c);
                        }

                        System.out.print("\nDigite o ID da categoria do novo produto: ");
                        long categoriaId = scanner.nextLong();
                        scanner.nextLine();
                        Categoria categoria = categoriaService.buscarCategoriaId(categoriaId);
                        categoriasEscolhidas.add(categoria);

                        System.out.print("Deseja adicionar mais uma categoria (N/S): ");
                        String adicionar = scanner.nextLine();
                        if (adicionar.equalsIgnoreCase("N")) {
                            cadastrar = false;
                        }
                    }

                    if (!cadastrar) {
                        Produto produto = new Produto(nome, descricao, preco, categoriasEscolhidas);
                        produtoService.cadastrarProduto(produto);
                    }
                    break;

                case 2:
                    List<Produto> produtos = produtoService.listarProdutos();
                    for (Produto p : produtos) {
                        System.out.println(p);
                    }
                    break;

                case 3:
                    List<String> produtosPorNome = produtoService.consultarProdutosPorNome();
                    for (String ps : produtosPorNome) {
                        System.out.println(ps);
                    }
                    break;

                case 4:
                    System.out.print("Digite o ID do produto a ser alterado: ");
                    long idAlterar = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Digite o novo nome do produto: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Digite a nova descrição do produto: ");
                    String novaDescricao = scanner.nextLine();
                    System.out.print("Digite o novo preço do produto: ");
                    double novoPreco = scanner.nextDouble();

                    Produto produtoAlterado = produtoService.alterarProduto(idAlterar, novoNome, novaDescricao, novoPreco);
                    System.out.println(produtoAlterado);
                    break;

                case 5:
                    System.out.print("Digite o ID do produto a ser removido: ");
                    long idRemover = scanner.nextLong();
                    scanner.nextLine();

                    produtoService.removerProduto(idRemover);
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
    }
}
