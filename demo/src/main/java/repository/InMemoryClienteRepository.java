package repository;

import model.Cliente;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryClienteRepository implements ClienteRepository {

    private final Map<Long, Cliente> database = new LinkedHashMap<>();
    private Long nextId = 1L;

    @Override
    public Cliente save(Cliente cliente) {
        if (cliente.getId() == null) {
            cliente.setId(getNextId());
        }
        database.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Cliente> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public boolean existsByEmail(String email) {
        return database.values().stream()
                .anyMatch(cliente -> cliente.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public Long getNextId() {
        return nextId++;
    }
}