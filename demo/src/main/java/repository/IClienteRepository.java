package repository;

import model.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteRepository {
    Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
    boolean existsByEmail(String email);
    Long getNextId();
}