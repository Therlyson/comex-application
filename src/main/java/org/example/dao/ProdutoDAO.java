package org.example.dao;

import org.example.model.Categoria;
import org.example.model.Produto;
import org.example.services.CategoriaService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Connection connection;

    public ProdutoDAO(Connection connection){
        this.connection = connection;
    }

    public Boolean cadastrarProduto(Produto produto) {
        String sql = "INSERT INTO produto(nome, descricao, preco, categoria_id) VALUES (?, ?, ?, ?)";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getPreco());
            ps.setLong(4, produto.getCategoria().getId());

            int resultado = ps.executeUpdate();
            return resultado==1;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Produto> listarProdutos(){
        String sql = "SELECT * FROM produto";
        List<Produto> produtos = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Produto produto = resultProduto(rs);
                produtos.add(produto);
            }

            rs.close();
            return produtos;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private Produto resultProduto(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String nome = rs.getString("nome");
        String descricao = rs.getString("descricao");
        Double preco = rs.getDouble("preco");
        CategoriaService categoriaService = new CategoriaService();
        Categoria categoria = categoriaService.buscarCategoriaId(rs.getLong("categoria_id"));
        return new Produto(id, nome, descricao, preco, categoria);
    }

    public List<String> consultarProdutosPorNome(){
        String sql = "SELECT nome FROM produto ORDER BY nome";
        List<String> produtos = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String nome = rs.getString("nome");
                produtos.add(nome);
            }

            rs.close();
            return produtos;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Produto buscarProduto(Long id){
        String sql = "SELECT * FROM produto WHERE id = " +id;

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Produto produto = resultProduto(rs);
                rs.close();
                return produto;
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public Produto alterarProduto(Produto produto){
        String sql = "UPDATE produto SET nome=?, descricao=?, preco=?, categoria_id=? WHERE id=?";

        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getPreco());
            ps.setLong(4, produto.getCategoria().getId());
            ps.setLong(5, produto.getId());

            ps.execute();
            return produto;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Boolean remover(Long id) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1, id);

            int resultado = ps.executeUpdate();
            return resultado==1;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
