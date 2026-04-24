package repository;

import model.Cliente;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileClienteRepository implements IClienteRepository {

    private final Path filePath;

    public FileClienteRepository(String fileName) {
        this.filePath = Path.of(fileName);
        createFileIfNecessary();
    }

    private void createFileIfNecessary() {
        try {
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar arquivo de dados.", e);
        }
    }

    @Override
    public Cliente save(Cliente cliente) {
        if (cliente.getId() == null) {
            cliente.setId(getNextId());
        }

        String line = cliente.getId() + ";" + cliente.getNome() + ";" + cliente.getEmail();

        try {
            Files.writeString(
                    filePath,
                    line + System.lineSeparator(),
                    StandardOpenOption.APPEND
            );
            return cliente;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar cliente no arquivo.", e);
        }
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return findAll().stream()
                .filter(cliente -> cliente.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Cliente> findAll() {
        try {
            List<String> lines = Files.readAllLines(filePath);
            List<Cliente> clientes = new ArrayList<>();

            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(";");
                Long id = Long.parseLong(parts[0]);
                String nome = parts[1];
                String email = parts[2];

                clientes.add(new Cliente(id, nome, email));
            }

            return clientes;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler clientes do arquivo.", e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return findAll().stream()
                .anyMatch(cliente -> cliente.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public Long getNextId() {
        return findAll().stream()
                .map(Cliente::getId)
                .max(Long::compareTo)
                .orElse(0L) + 1;
    }
}