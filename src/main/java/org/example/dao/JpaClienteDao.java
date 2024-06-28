package org.example.dao;

import org.example.model.Cliente;

import javax.persistence.EntityManager;
import java.util.List;

public class JpaClienteDao {
    private EntityManager manager;

    public JpaClienteDao(EntityManager manager) {
        this.manager = manager;
    }

    public void salvar(Cliente cliente) {
        this.manager.getTransaction().begin();
        this.manager.persist(cliente);
        this.manager.getTransaction().commit();
    }

    public List<Cliente> listar(){
        String jpql = "SELECT c FROM Cliente c";
        return manager.createQuery(jpql, Cliente.class).getResultList();
    }

    public Cliente pesquisarPorId(Long id){
        return manager.find(Cliente.class, id);
    }


    public Cliente atualizar(Cliente cliente){
        this.manager.getTransaction().begin();
        cliente = this.manager.merge(cliente);
        this.manager.getTransaction().commit();

        return cliente;
    }

    public void remover(Cliente cliente) {
        this.manager.getTransaction().begin();
        cliente = this.manager.merge(cliente);
        this.manager.remove(cliente);
        this.manager.getTransaction().commit();
    }

}
