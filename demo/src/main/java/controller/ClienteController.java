package controller;

import model.Cliente;
import service.ClienteService;

import java.util.List;
import java.util.Scanner;

public class ClienteController {

    private final ClienteService clienteService;
    private final Scanner scanner;

    public ClienteController(ClienteService clienteService, Scanner scanner) {
        this.clienteService = clienteService;
        this.scanner = scanner;
    }

    public void start() {
        int option = -1;

        while (option != 0) {
            printMenu();
            option = readInteger("Escolha uma opção: ");

            switch (option) {
                case 1 -> createCliente();
                case 2 -> findClienteById();
                case 3 -> listClientes();
                case 0 -> System.out.println("Encerrando sistema...");
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("===== SISTEMA DE CLIENTES =====");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Buscar cliente por ID");
        System.out.println("3 - Listar clientes");
        System.out.println("0 - Sair");
    }

    private void createCliente() {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            Cliente cliente = clienteService.create(nome, email);
            System.out.println("Cliente cadastrado com sucesso: " + cliente);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void findClienteById() {
        try {
            Long id = Long.valueOf(readInteger("Informe o ID: "));
            Cliente cliente = clienteService.findById(id);
            System.out.println("Cliente encontrado: " + cliente);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listClientes() {
        List<Cliente> clientes = clienteService.findAll();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("=== LISTA DE CLIENTES ===");
        clientes.forEach(System.out::println);
    }

    private int readInteger(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }
}