package org.example.dao;

import org.example.model.Categoria;
import org.example.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    private Connection connection;

    public CategoriaDAO(Connection connection) {
        this.connection = connection;
    }

    public Boolean cadastrarCategoria(Categoria categoria) {
        String sql = "INSERT INTO categoria(nome, descricao) VALUES (?, ?)";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getDescricao());

            int resultado = ps.executeUpdate();
            return resultado==1;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Categoria> listarCategorias(){
        String sql = "SELECT * FROM categoria";
        List<Categoria> categorias = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Categoria categoria = resultCategoria(rs);
                categorias.add(categoria);
            }

            rs.close();
            return categorias;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private Categoria resultCategoria(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String nome = rs.getString("nome");
        String descricao = rs.getString("descricao");
        return new Categoria(id, nome, descricao);
    }

    public List<String> consultarCategoriasPorNome(){
        String sql = "SELECT nome FROM categoria ORDER BY nome";
        List<String> categorias = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String nome = rs.getString("nome");
                categorias.add(nome);
            }

            rs.close();
            return categorias;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public Categoria buscarCategoria(Long id){
        String sql = "SELECT * FROM categoria WHERE id = " + id;

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Categoria categoria = resultCategoria(rs);
                rs.close();
                return categoria;
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public Categoria alterar(Categoria categoria){
        String sql = "UPDATE categoria SET nome=?, descricao=? WHERE id=?";

        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getDescricao());
            ps.setLong(3, categoria.getId());

            ps.execute();
            return categoria;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Boolean remover(Long id) {
        String sql = "DELETE FROM categoria WHERE id = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1, id);

            int resultado = ps.executeUpdate();
            return resultado==1;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}