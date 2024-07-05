package org.example.dao;

import org.example.model.Pedido;

import javax.persistence.EntityManager;
import java.util.List;

public class PedidoDAO {
    private EntityManager manager;

    public PedidoDAO(EntityManager manager) {
        this.manager = manager;
    }

    public void cadastrar(Pedido pedido){
        this.manager.getTransaction().begin();
        this.manager.persist(pedido);
        this.manager.getTransaction().commit();
    }

    public Pedido buscarPorId(Long id){
        return this.manager.find(Pedido.class, id);
    }

    public List<Pedido> listarTodos(){
        String jpql = "SELECT p FROM Pedido p";
        return manager.createQuery(jpql, Pedido.class).getResultList();
    }
}
