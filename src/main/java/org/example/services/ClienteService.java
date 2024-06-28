package org.example.services;

import org.example.dao.JpaClienteDao;
import org.example.exception.ClienteException;
import org.example.model.Cliente;

import javax.persistence.EntityManager;
import java.util.List;

public class ClienteService {
    private JpaClienteDao clienteDao;

    public ClienteService(EntityManager manager) {
        this.clienteDao = new JpaClienteDao(manager);
    }

    public void salvarCliente(Cliente cliente) throws ClienteException {
        try {
            clienteDao.salvar(cliente);
        } catch (Exception e) {
            throw new ClienteException("Erro ao salvar o cliente no banco de dados.", e);
        }
    }

    public List<Cliente> listarTodosOsClientes() throws ClienteException {
        try {
            List<Cliente> clientes = clienteDao.listar();
            return clientes;
        } catch (Exception e) {
            throw new ClienteException("Erro ao listar clientes do banco de dados.", e);
        }
    }

        public Cliente pesquisarClientePorId(Long id) throws ClienteException {
        try {
            Cliente cliente = clienteDao.pesquisarPorId(id);
            return cliente;
        } catch (Exception e) {
            throw new ClienteException("Erro ao pesquisar cliente do banco de dados.", e);
        }
    }

    public Cliente atualizarCliente(Cliente cliente) throws ClienteException{
        try{
            Cliente clienteAtualizado = clienteDao.atualizar(cliente);
            return clienteAtualizado;
        }catch (Exception e){
            throw new ClienteException("Erro ao tentar atualizar cliente do banco de dados.", e);
        }
    }

    public void removerCLiente(Long id) throws ClienteException {
        Cliente cliente = pesquisarClientePorId(id);
        try{
            clienteDao.remover(cliente);
        }catch (Exception e){
            throw new ClienteException("Erro ao tentar remover cliente do banco de dados.", e);
        }
    }
}
