package org.example.services;

import org.example.conexao.ConnectionFactory;
import org.example.dao.CategoriaDAO;
import org.example.model.Categoria;

import java.sql.Connection;
import java.util.List;

public class CategoriaService {
    private ConnectionFactory factory;

    public CategoriaService(){
        factory = new ConnectionFactory();
    }

    private CategoriaDAO categoriaDAO(){
        Connection connection = factory.criaConexao();
        return new CategoriaDAO(connection);
    }

    public Boolean cadastrarCategoria(Categoria categoria){
        return categoriaDAO().cadastrarCategoria(categoria);
    }

    public List<Categoria> listarCategorias(){
        return categoriaDAO().listarCategorias();
    }

    public List<String> consultarCategoriasPorNome(){
        return categoriaDAO().consultarCategoriasPorNome();
    }

    public Categoria alterarCategoria(Long id, String nome, String descricao){
        Categoria categoria = categoriaDAO().buscarCategoria(id);
        if(categoria != null){
            categoria.setNome(nome);
            categoria.setDescricao(descricao);
            return categoriaDAO().alterar(categoria);
        }
        return null;
    }

    public Boolean removerCategoria(Long id){
        return categoriaDAO().remover(id);
    }
}
