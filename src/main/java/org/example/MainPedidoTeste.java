package org.example;

import org.example.dao.JpaClienteDao;
import org.example.dao.JpaProdutoDao;
import org.example.model.Cliente;
import org.example.model.ItemDePedido;
import org.example.model.Pedido;
import org.example.model.Produto;
import org.example.model.enums.TipoDesconto;
import org.example.model.enums.TipoDescontoProduto;
import org.example.services.ClienteService;
import org.example.services.PedidoService;
import org.example.utils.JPAutil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class MainPedidoTeste {
    public static void main(String[] args) {
        EntityManager manager = JPAutil.getEntityManager();
        PedidoService pedidoService = new PedidoService(manager);

        // Criando clientes e itens de pedido para teste\
        ClienteService clienteService = new ClienteService(manager);
        Cliente cliente = new Cliente("12345678900", "João Silva", "joao@gmail.com", "123456789", "12345678");
        Cliente cliente2 = new Cliente("98765432100", "Maria Souza", "maria@gmail.com", "987654321", "87654321");
        clienteService.salvarCliente(cliente);
        clienteService.salvarCliente(cliente2);

        JpaProdutoDao produtoService = new JpaProdutoDao(manager);
        Produto produto1 = produtoService.buscarPorId(1L);
        Produto produto2 = produtoService.buscarPorId(2L);
        Produto produto3 = produtoService.buscarPorId(3L);

        // Criando itens de pedido
            ItemDePedido item1 = new ItemDePedido(2, produto1.getPreco(), BigDecimal.valueOf(10), produto1, null, TipoDescontoProduto.PROMOCAO);
        ItemDePedido item2 = new ItemDePedido(1, produto2.getPreco(), BigDecimal.valueOf(20), produto2, null, TipoDescontoProduto.QUANTIDADE);
        ItemDePedido item3 = new ItemDePedido(3, produto3.getPreco(), BigDecimal.valueOf(5), produto3, null, TipoDescontoProduto.NENHUM);

        // Criando pedidos
        Pedido pedido1 = new Pedido(BigDecimal.valueOf(10), TipoDesconto.FIDELIDADE, cliente);
        pedido1.adicionarItem(item1);
        pedido1.adicionarItem(item2);

        Pedido pedido2 = new Pedido(BigDecimal.valueOf(20), TipoDesconto.FIDELIDADE, cliente2);
        pedido2.adicionarItem(item3);

        // Cadastrando pedidos
        pedidoService.cadastrarPedido(pedido1);
        pedidoService.cadastrarPedido(pedido2);

        // Listando todos os pedidos
        List<Pedido> pedidos = pedidoService.listarPedidos();
        System.out.println("Pedidos cadastrados:");
        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
        }

        // Buscando pedido por ID
        Long idBusca = 1L; // ID do pedido a ser buscado
        Pedido pedidoBuscado = pedidoService.buscarPedidoId(idBusca);
        if (pedidoBuscado != null) {
            System.out.println("Pedido encontrado: " + pedidoBuscado);
        } else {
            System.out.println("Pedido com ID " + idBusca + " não encontrado.");
        }

        manager.close();

    }
}
