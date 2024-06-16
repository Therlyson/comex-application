package org.example.services;

import org.example.dao.ClienteDAO;
import org.example.model.Cliente;

import java.sql.Connection;
import java.util.List;

public class ClienteService {
    private ConnectionFactory factory;

    public ClienteService(){
        factory = new ConnectionFactory();
    }

    private ClienteDAO clienteDAO(){
        Connection connection = factory.criaConexao();
        return new ClienteDAO(connection);
    }

    public Boolean salvarNoBanco(Cliente cliente){
        return clienteDAO().salvar(cliente);
    }

    public List<Cliente> listarClientesDoBanco(){
        return clienteDAO().listar();
    }

    public Cliente buscarCliente(Long id){
        return clienteDAO().buscarCliente(id);
    }

    public Cliente atualizarCliente(Cliente cliente){
        return clienteDAO().atualizar(cliente);
    }

    public Boolean removerCLiente(Long id){
        if(buscarCliente(id) != null){
            return clienteDAO().remover(id);
        }
        return false;
    }
}
