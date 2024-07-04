package org.example.services;

import org.example.conexao.ConnectionFactory;
import org.example.dao.JpaProdutoDao;
import org.example.exception.ComexException;
import org.example.model.Categoria;
import org.example.model.Produto;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.util.List;

public class ProdutoService {
    private JpaProdutoDao produtoDao;

    public ProdutoService(EntityManager manager){
        this.produtoDao = new JpaProdutoDao(manager);
    }

    public void cadastrarProduto(Produto produto){
        try {
            produtoDao.cadastrar(produto);
        } catch (Exception e) {
            throw new ComexException("Erro ao cadastrar o produto no banco de dados.", e);
        }
    }

    public List<Produto> listarProdutos(){
        try {
            return produtoDao.listar();
        } catch (Exception e) {
            throw new ComexException("Erro ao listar os produtos do banco de dados.", e);
        }
    }

    public List<String> consultarProdutosPorNome(){
        try {
            return produtoDao.consultarPorNome();
        } catch (Exception e) {
            throw new ComexException("Erro ao listar os produtos do banco de dados.", e);
        }
    }

    public Produto alterarProduto(Long id, String nome, String descricao, Double preco){
        Produto produto = produtoDao.buscarPorId(id);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        try {
            return produtoDao.alterar(produto);
        } catch (Exception e) {
            throw new ComexException("Erro ao alterar o produto do banco de dados.", e);
        }
    }

    public void removerProduto(Long id){
        Produto produto = produtoDao.buscarPorId(id);
        try {
            produtoDao.remover(produto);
        } catch (Exception e) {
            throw new ComexException("Erro ao listar os produtos do banco de dados.", e);
        }
    }
}
