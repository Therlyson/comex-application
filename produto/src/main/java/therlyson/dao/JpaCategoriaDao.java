package therlyson.dao;

import therlyson.model.Categoria;

import javax.persistence.EntityManager;
import java.util.List;

public class JpaCategoriaDao {
    private EntityManager manager;

    public JpaCategoriaDao(EntityManager manager) {
        this.manager = manager;
    }

    public void cadastrar(Categoria categoria) {
        this.manager.getTransaction().begin();
        this.manager.persist(categoria);
        this.manager.getTransaction().commit();
    }

    public List<Categoria> listar(){
        String jpql = "SELECT c FROM Categoria c";
        return manager.createQuery(jpql, Categoria.class).getResultList();
    }

    public List<String> consultarPorNome() {
        String jpql = "SELECT c.nome FROM Categoria c ORDER BY c.nome";
        return manager.createQuery(jpql, String.class).getResultList();
    }

    public Categoria buscarPorId(Long id){
        return manager.find(Categoria.class, id);
    }


    public Categoria alterar(Categoria categoria){
        this.manager.getTransaction().begin();
        categoria = this.manager.merge(categoria);
        this.manager.getTransaction().commit();

        return categoria;
    }

    public void remover(Categoria categoria) {
        this.manager.getTransaction().begin();
        categoria = this.manager.merge(categoria);
        this.manager.remove(categoria);
        this.manager.getTransaction().commit();
    }

}