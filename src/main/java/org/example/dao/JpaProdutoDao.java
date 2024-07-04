package org.example.dao;

import org.example.model.Produto;

import javax.persistence.EntityManager;
import java.util.List;

public class JpaProdutoDao {
    private EntityManager manager;

    public JpaProdutoDao(EntityManager manager){
        this.manager = manager;
    }

    public void cadastrar(Produto produto) {
        this.manager.getTransaction().begin();
        this.manager.persist(produto);
        this.manager.getTransaction().commit();
    }

    public List<Produto> listar(){
        String jpql = "SELECT p FROM Produto p";
        return manager.createQuery(jpql, Produto.class).getResultList();
    }

    public List<String> consultarPorNome(){
        String jpql = "SELECT p.nome FROM Produto p ORDER BY p.nome";
        return manager.createQuery(jpql, String.class).getResultList();
    }

    public Produto buscarPorId(Long id){
        return manager.find(Produto.class, id);
    }


    public Produto alterar(Produto produto){
        this.manager.getTransaction().begin();
        produto = this.manager.merge(produto);
        this.manager.getTransaction().commit();

        return produto;
    }

    public void remover(Produto produto) {
        this.manager.getTransaction().begin();
        produto = this.manager.merge(produto);
        this.manager.remove(produto);
        this.manager.getTransaction().commit();
    }
}
