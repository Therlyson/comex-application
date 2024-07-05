package org.example.services;

import org.example.dao.JpaClienteDao;
import org.example.exception.ComexException;
import org.example.model.Cliente;

import javax.persistence.EntityManager;
import java.util.List;

public class ClienteService {
    private JpaClienteDao clienteDao;

    public ClienteService(EntityManager manager) {
        this.clienteDao = new JpaClienteDao(manager);
    }

    public void salvarCliente(Cliente cliente) throws ComexException {
        try {
            clienteDao.salvar(cliente);
        } catch (Exception e) {
            throw new ComexException("Erro ao salvar o cliente no banco de dados.", e);
        }
    }

    public List<Cliente> listarTodosOsClientes() throws ComexException {
        try {
            List<Cliente> clientes = clienteDao.listar();
            return clientes;
        } catch (Exception e) {
            throw new ComexException("Erro ao listar clientes do banco de dados.", e);
        }
    }

        public Cliente pesquisarClientePorId(Long id) throws ComexException {
        try {
            Cliente cliente = clienteDao.pesquisarPorId(id);
            return cliente;
        } catch (Exception e) {
            throw new ComexException("Erro ao pesquisar cliente do banco de dados.", e);
        }
    }

    public Cliente atualizarCliente(Cliente cliente) throws ComexException{
        try{
            Cliente clienteAtualizado = clienteDao.atualizar(cliente);
            return clienteAtualizado;
        }catch (Exception e){
            throw new ComexException("Erro ao tentar atualizar cliente do banco de dados.", e);
        }
    }

    public void removerCLiente(Long id) throws ComexException {
        Cliente cliente = pesquisarClientePorId(id);
        try{
            clienteDao.remover(cliente);
        }catch (Exception e){
            throw new ComexException("Erro ao tentar remover cliente do banco de dados.", e);
        }
    }

    public List<String> listarClientesPorNome() throws ComexException {
        try{
            return clienteDao.listarPorNome();
        }catch (Exception e){
            throw new ComexException("Erro ao tentar listar clientes do banco de dados.", e);
        }
    }
}
