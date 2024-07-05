package org.example;

import org.example.dao.JpaProdutoDao;
import org.example.model.Cliente;
import org.example.model.ItemDePedido;
import org.example.model.Pedido;
import org.example.model.Produto;
import org.example.model.enums.TipoDesconto;
import org.example.model.enums.TipoDescontoProduto;
import org.example.services.ClienteService;
import org.example.services.PedidoService;
import org.example.services.ProdutoService;
import org.example.utils.JPAutil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class MainPedidoTeste {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManager manager = JPAutil.getEntityManager();

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Cadastrar Pedido");
            System.out.println("2. Buscar Pedido por ID");
            System.out.println("3. Listar todos os Pedidos");
            System.out.println("4. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1:
                    cadastrarPedido(manager, scanner);
                    break;
                case 2:
                    buscarPedidoPorId(manager, scanner);
                    break;
                case 3:
                    listarTodosOsPedidos(manager);
                    break;
                case 4:
                    manager.close();
                    scanner.close();
                    System.out.println("Programa encerrado.");
                    return;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    public static void cadastrarPedido(EntityManager manager, Scanner scanner) {
        PedidoService pedidoService = new PedidoService(manager);
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
    }

    public static void buscarPedidoPorId(EntityManager manager, Scanner scanner) {
        PedidoService pedidoService = new PedidoService(manager);

        System.out.print("Digite o ID do pedido a ser buscado: ");
        Long idBusca = scanner.nextLong();
        scanner.nextLine(); // Consumir nova linha

        Pedido pedidoBuscado = pedidoService.buscarPedidoId(idBusca);
        if (pedidoBuscado != null) {
            System.out.println("Pedido encontrado: " + pedidoBuscado);
        } else {
            System.out.println("Pedido com ID " + idBusca + " não encontrado.");
        }
    }

    public static void listarTodosOsPedidos(EntityManager manager) {
        PedidoService pedidoService = new PedidoService(manager);

        List<Pedido> pedidos = pedidoService.listarPedidos();
        System.out.println("Pedidos cadastrados:");
        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
        }
    }
}
