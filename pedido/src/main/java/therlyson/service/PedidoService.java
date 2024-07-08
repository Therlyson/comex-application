package therlyson.service;

import therlyson.dao.PedidoDAO;
import therlyson.exception.ComexException;
import therlyson.model.Pedido;

import javax.persistence.EntityManager;
import java.time.LocalDate;
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

    public List<Pedido> buscarPedidosPorData(LocalDate data){
        try{
            List<Pedido> pedidos = pedidoDAO.buscarPorData(data);
            return pedidos;
        }catch (Exception e){
            throw new ComexException("Erro! Não foi possivel listar os pedidos com base na data do banco de dados.", e);
        }
    }

}
