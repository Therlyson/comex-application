package org.example.services;

import org.example.dao.PedidoDAO;
import org.example.exception.ComexException;
import org.example.model.Pedido;

import javax.persistence.EntityManager;
import java.util.List;

public class PedidoService {
    private PedidoDAO pedidoDAO;

    public PedidoService(EntityManager manager) {
        this.pedidoDAO = new PedidoDAO(manager);
    }

    public void cadastrarPedido(Pedido pedido){
        try {
            pedidoDAO.cadastrar(pedido);
        } catch (Exception e) {
            throw new ComexException("Erro ao tentar cadastrar pedido no banco de dados.", e);
        }
    }

    public List<Pedido> listarPedidos(){
        try{
            List<Pedido> pedidos = pedidoDAO.listarTodos();
            return pedidos;
        }catch (Exception e){
            throw new ComexException("Erro! Não foi possivel listar os pedidos do banco de dados.", e);
        }
    }

    public Pedido buscarPedidoId(Long id){
        try{
            Pedido pedido = pedidoDAO.buscarPorId(id);
            return pedido;
        }catch (Exception e){
            throw new ComexException("Erro! Não foi possivel buscar esse pedido do banco de dados.", e);
        }
    }
}
