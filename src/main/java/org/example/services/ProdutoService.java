package org.example.services;

import org.example.conexao.ConnectionFactory;
import org.example.dao.ProdutoDAO;
import org.example.model.Categoria;
import org.example.model.Produto;

import java.sql.Connection;
import java.util.List;

public class ProdutoService {
    private ConnectionFactory factory;

    public ProdutoService(){
        factory = new ConnectionFactory();
    }

    private ProdutoDAO produtoDAO(){
        Connection connection = factory.criaConexao();
        return new ProdutoDAO(connection);
    }

    public Boolean cadastrarProduto(Produto produto){
        return produtoDAO().cadastrarProduto(produto);
    }

    public List<Produto> listarProdutos(){
        return produtoDAO().listarProdutos();
    }

    public List<String> consultarProdutosPorNome(){
        return produtoDAO().consultarProdutosPorNome();
    }

    public Produto alterarProduto(Long id, String nome, String descricao, Double preco, Categoria categoria){
        Produto produto = produtoDAO().buscarProduto(id);
        if(produto != null){
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPreco(preco);
            produto.setCategoria(categoria);
            return produtoDAO().alterarProduto(produto);
        }
        return null;
    }

    public Boolean removerProduto(Long id){
        return produtoDAO().remover(id);
    }
}
