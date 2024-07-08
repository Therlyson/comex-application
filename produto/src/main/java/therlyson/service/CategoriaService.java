package therlyson.service;

import therlyson.dao.JpaCategoriaDao;
import therlyson.exception.ComexException;
import therlyson.model.Categoria;

import javax.persistence.EntityManager;
import java.util.List;

public class CategoriaService {
    private JpaCategoriaDao categoriaDao;

    public CategoriaService(EntityManager manager) {
        this.categoriaDao = new JpaCategoriaDao(manager);
    }

    public void cadastrarCategoria(Categoria categoria){
        try {
            categoriaDao.cadastrar(categoria);
        } catch (Exception e) {
            throw new ComexException("Erro ao tentar cadastrar categoria no banco de dados.", e);
        }
    }

    public List<Categoria> listarCategorias(){
        try{
            List<Categoria> categorias = categoriaDao.listar();
            return categorias;
        }catch (Exception e){
            throw new ComexException("Erro! Não foi possivel listar as categorias do banco de dados.", e);
        }
    }

    public List<String> consultarCategoriasPorNome(){
        try{
            List<String> categoriasNome = categoriaDao.consultarPorNome();
            return categoriasNome;
        }catch (Exception e){
            throw new ComexException("Erro! Não foi possivel listar as categorias do banco de dados.", e);
        }
    }

    public Categoria buscarCategoriaId(Long id){
        try{
            Categoria categoria = categoriaDao.buscarPorId(id);
            return categoria;
        }catch (Exception e){
            throw new ComexException("Erro! Não foi possivel buscar essa categoria do banco de dados.", e);
        }
    }

    public Categoria alterarCategoria(Categoria categoria){
        try{
            Categoria novaCategoria = categoriaDao.alterar(categoria);
            return novaCategoria;
        }catch (Exception e){
            throw new ComexException("Erro! Não foi possivel alterar essa categoria.", e);
        }
    }

    public void removerCategoria(Long id){
        Categoria categoria = categoriaDao.buscarPorId(id);
        try{
            categoriaDao.remover(categoria);
        }catch (Exception e){
            throw new ComexException("Erro! não foi possivel remover essa categoria do banco de dados.", e);
        }
    }
}
