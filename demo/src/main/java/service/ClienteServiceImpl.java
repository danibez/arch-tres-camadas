package service;

import model.Cliente;
import repository.IClienteRepository;

import java.util.List;

public class ClienteServiceImpl implements IClienteService {

    private final IClienteRepository clienteRepository;

    public ClienteServiceImpl(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente create(String nome, String email) {
        validarNome(nome);
        validarEmail(email);

        if (clienteRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Já existe cliente cadastrado com esse email.");
        }

        Cliente cliente = new Cliente(null, nome.trim(), email.trim());
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email é obrigatório.");
        }

        String emailLimpo = email.trim();

        if (!emailLimpo.contains("@") || emailLimpo.startsWith("@") || emailLimpo.endsWith("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }
    }
}