package therlyson;

import therlyson.model.Cliente;
import therlyson.model.ItemDePedido;
import therlyson.model.Pedido;
import therlyson.model.Produto;
import therlyson.model.enums.TipoDesconto;
import therlyson.model.enums.TipoDescontoProduto;
import therlyson.service.ClienteService;
import therlyson.service.PedidoService;
import therlyson.service.ProdutoService;
import therlyson.utils.JPAutil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MainPedido {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManager manager = JPAutil.getEntityManager();
        PedidoService pedidoService = new PedidoService(manager);
        boolean running = true;

        while (running) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Cadastrar Pedido");
            System.out.println("2. Buscar Pedido por ID");
            System.out.println("3. Listar todos os Pedidos");
            System.out.println("4. Buscar pedidos por data");
            System.out.println("5. sair");

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
                        break;
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
                    System.out.println("Saindo...");
                    scanner.close();
                    manager.close();
                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida, tente novamente.");
                    break;
            }
        }
    }
}
