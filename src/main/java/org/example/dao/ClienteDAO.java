package org.example.dao;

import org.example.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection connection;

    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public Boolean salvar(Cliente cliente) {
        String sql = "INSERT INTO cliente(cpf, nome, email, telefone, cep) VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, cliente.getCpf());
            ps.setString(2, cliente.getNome());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getTelefone());
            ps.setString(5, cliente.getCep());

            int resultado = ps.executeUpdate();
            return resultado==1;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Cliente> listar(){
        String sql = "SELECT * FROM cliente";
        List<Cliente> clientes = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Cliente cliente = resultCliente(rs);
                clientes.add(cliente);
            }

            rs.close();
            return clientes;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Cliente buscarCliente(Long id){
        String sql = "SELECT * FROM cliente WHERE id = " + id;

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Cliente cliente = resultCliente(rs);
                rs.close();
                return cliente;
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private Cliente resultCliente(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String cpf = rs.getString("cpf");
        String nome = rs.getString("nome");
        String email = rs.getString("email");
        String telefone = rs.getString("telefone");
        String cep = rs.getString("cep");

        return new Cliente(id, cpf, nome, email, telefone, cep);
    }

    public Cliente atualizar(Cliente cliente){
        String sql = "UPDATE cliente SET cpf=?, nome=?, email=?, telefone=?, cep=? WHERE id=?";

        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, cliente.getCpf());
            ps.setString(2, cliente.getNome());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getTelefone());
            ps.setString(5, cliente.getCep());

            ps.setLong(6, cliente.getId());

            ps.execute();
            return cliente;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Boolean remover(Long id) {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1, id);

            int resultado = ps.executeUpdate();
            return resultado==1;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


}
