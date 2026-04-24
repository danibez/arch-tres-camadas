package app;

import controller.ClienteController;
import repository.IClienteRepository;
import repository.FileClienteRepository;
import repository.InMemoryClienteRepository;
import service.IClienteService;
import service.ClienteServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha o tipo de repositório:");
        System.out.println("1 - Em memória");
        System.out.println("2 - Em arquivo");
        System.out.print("Opção: ");

        String option = scanner.nextLine();

        IClienteRepository clienteRepository;

        if ("2".equals(option)) {
            clienteRepository = new FileClienteRepository("clientes.txt");
            System.out.println("Repositório em arquivo selecionado.");
        } else {
            clienteRepository = new InMemoryClienteRepository();
            System.out.println("Repositório em memória selecionado.");
        }

        IClienteService clienteService = new ClienteServiceImpl(clienteRepository);
        ClienteController clienteController = new ClienteController(clienteService, scanner);

        clienteController.start();
    }
}