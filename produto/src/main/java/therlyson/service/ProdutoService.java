package therlyson.service;

import therlyson.dao.JpaProdutoDao;
import therlyson.exception.ComexException;
import therlyson.model.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
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

    public Produto buscarProdutoId(Long id){
        try{
            Produto produto = produtoDao.buscarPorId(id);
            return produto;
        }catch (Exception e){
            throw new ComexException("Erro! Não foi possivel buscar esse produto do banco de dados.", e);
        }
    }

    public List<String> consultarProdutosPorNome(){
        try {
            return produtoDao.consultarPorNome();
        } catch (Exception e) {
            throw new ComexException("Erro ao listar os produtos do banco de dados.", e);
        }
    }

    public Produto alterarProduto(Long id, String nome, String descricao, BigDecimal preco){
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

    public List<Produto> listarProdutosPorCategoria(Long id){
        try{
            return produtoDao.listarPorCategoria(id);
        }catch (Exception e){
            throw new ComexException("Erro ao listar os produtos por uma categoria no banco de dados.", e);
        }
    }
}
