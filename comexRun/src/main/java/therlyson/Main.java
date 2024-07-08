package therlyson;

import therlyson.model.*;
import therlyson.model.enums.TipoDesconto;
import therlyson.model.enums.TipoDescontoProduto;
import therlyson.service.*;
import therlyson.utils.JPAutil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            System.out.println("4. Operações com Pedidos");
            System.out.println("5. Sair");
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
                    operacoesPedido(scanner, manager);
                    break;

                case 5:
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
            System.out.println("6. Listar clientes por nome");
            System.out.println("7. Voltar ao menu principal");
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
            System.out.println("6. Listar produtos por categoria");
            System.out.println("7. Voltar ao menu principal");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do produto: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite a descrição do produto: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Digite o preço do produto: ");
                    BigDecimal preco = scanner.nextBigDecimal();

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
                        System.out.println(" - " + ps);
                    }
                    break;

                case 4:
                    System.out.print("\nDigite o ID do produto a ser alterado: ");
                    long idAlterar = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Digite o novo nome do produto: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Digite a nova descrição do produto: ");
                    String novaDescricao = scanner.nextLine();
                    System.out.print("Digite o novo preço do produto: ");
                    BigDecimal novoPreco = scanner.nextBigDecimal();

                    Produto produtoAlterado = produtoService.alterarProduto(idAlterar, novoNome, novaDescricao, novoPreco);
                    System.out.println(produtoAlterado);
                    break;

                case 5:
                    System.out.print("\nDigite o ID do produto a ser removido: ");
                    long idRemover = scanner.nextLong();
                    scanner.nextLine();

                    produtoService.removerProduto(idRemover);
                    break;

                case 6:
                    System.out.println("\nDigite o ID da categoria que deseja listar os produtos: ");
                    long idCategoria = scanner.nextLong();
                    scanner.nextLine();
                    Categoria categoria = categoriaService.buscarCategoriaId(idCategoria);
                    if(categoria!=null){
                        List<Produto> produtosCategoria = produtoService.listarProdutosPorCategoria(idCategoria);
                        produtosCategoria.forEach(System.out::println);
                    }else {
                        System.out.println("Erro! não existe essa categoria cadastrada!");
                    }
                    break;

                case 7:
                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
            System.out.println();
        }
    }

    private static void operacoesPedido(Scanner scanner, EntityManager manager){
        PedidoService pedidoService = new PedidoService(manager);
        boolean running = true;

        while (running) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Cadastrar Pedido");
            System.out.println("2. Buscar Pedido por ID");
            System.out.println("3. Listar todos os Pedidos");
            System.out.println("4. Buscar pedidos por data");
            System.out.println("5. Voltar ao menu principal");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1:
                    ClienteService clienteService = new ClienteService(manager);
                    ProdutoService produtoService = new ProdutoService(manager);

                    System.out.print("Digite o id do cliente que fez o pedido: ");
                    Long idCliente = scanner.nextLong();
                    Cliente cliente = clienteService.pesquisarClientePorId(idCliente);
                    if (cliente == null) {
                        System.out.println("Cliente não encontrado. Por favor, cadastre o cliente primeiro.");
                        return;
                    }

                    System.out.println("Digite o desconto do pedido:");
                    BigDecimal descontoPedido = scanner.nextBigDecimal();
                    scanner.nextLine(); // Consumir nova linha

                    System.out.println("Escolha o tipo de desconto (FIDELIDADE ou NENHUM):");
                    String tipoDescontoStr = scanner.nextLine();
                    TipoDesconto tipoDesconto = TipoDesconto.valueOf(tipoDescontoStr);

                    Pedido pedido = new Pedido(descontoPedido, tipoDesconto, cliente);

                    while (true) {
                        System.out.println("Deseja adicionar um item ao pedido? (s/n)");
                        String resposta = scanner.nextLine();
                        if (resposta.equalsIgnoreCase("n")) {
                            break;
                        }

                        System.out.println("Digite o ID do produto:");
                        Long idProduto = scanner.nextLong();
                        Produto produto = produtoService.buscarProdutoId(idProduto);
                        if (produto == null) {
                            System.out.println("Produto não encontrado.");
                            continue;
                        }

                        System.out.println("Digite a quantidade:");
                        int quantidade = scanner.nextInt();

                        System.out.println("Digite o desconto do item:");
                        BigDecimal descontoItem = scanner.nextBigDecimal();
                        scanner.nextLine(); // Consumir nova linha

                        System.out.println("Escolha o tipo de desconto do item (PROMOCAO, QUANTIDADE, NENHUM):");
                        String tipoDescontoItemStr = scanner.nextLine();
                        TipoDescontoProduto tipoDescontoItem = TipoDescontoProduto.valueOf(tipoDescontoItemStr);

                        ItemDePedido itemDePedido = new ItemDePedido(quantidade, produto.getPreco(), descontoItem, produto, pedido, tipoDescontoItem);
                        pedido.adicionarItem(itemDePedido);
                    }

                    pedidoService.cadastrarPedido(pedido);
                    System.out.println("Pedido cadastrado com sucesso!");
                    break;
                case 2:
                    System.out.print("\nDigite o ID do pedido a ser buscado: ");
                    Long idBusca = scanner.nextLong();
                    scanner.nextLine();

                    Pedido pedidoBuscado = pedidoService.buscarPedidoId(idBusca);
                    if (pedidoBuscado != null) {
                        System.out.println("Pedido encontrado: " + pedidoBuscado);
                    } else {
                        System.out.println("Pedido com ID " + idBusca + " não encontrado.");
                    }
                    break;
                case 3:
                    List<Pedido> pedidos = pedidoService.listarPedidos();
                    System.out.println("\nPedidos cadastrados:");
                    for (Pedido pedido1 : pedidos) {
                        System.out.println(pedido1);
                    }
                    break;

                case 4:
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    System.out.println("\nDigite a data que está buscando os pedidos(EX: 2024-07-05): ");
                    String dataString = scanner.nextLine();

                    LocalDate data = LocalDate.parse(dataString, formatter);
                    List<Pedido> pedidosPorData = pedidoService.buscarPedidosPorData(data);
                    if(pedidosPorData.isEmpty()) {
                        System.out.println("Não existem pedidos para essa data");
                    }else{
                        pedidosPorData.forEach(System.out::println);
                    }
                    break;

                case 5:
                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida, tente novamente.");
                    break;
            }
        }
    }
}
