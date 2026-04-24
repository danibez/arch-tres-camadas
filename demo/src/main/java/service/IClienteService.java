package service;

import model.Cliente;

import java.util.List;

public interface IClienteService {
    Cliente create(String nome, String email);
    Cliente findById(Long id);
    List<Cliente> findAll();
}