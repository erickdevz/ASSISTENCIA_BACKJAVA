package com.example.assistencia;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.assistencia.model.Cliente;
import com.example.assistencia.repository.ClienteRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final ClienteRepository clienteRepository;

    public DatabaseSeeder(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Adiciona alguns clientes ao iniciar o sistema
        Cliente cliente1 = new Cliente();
        cliente1.setNome("João da Silva");
        cliente1.setCpfCnpj("12345678900");
        cliente1.setEmail("joao@email.com");
        cliente1.setTelefone("32999998888");
        cliente1.setCidade("Viçosa");
        cliente1.setEstado("MG");

        clienteRepository.save(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Maria Oliveira");
        cliente2.setCpfCnpj("98765432100");
        cliente2.setEmail("maria@email.com");
        cliente2.setTelefone("32988887777");
        cliente2.setCidade("Cataguases");
        cliente2.setEstado("MG");

        clienteRepository.save(cliente2);

        System.out.println("Clientes iniciais salvos no banco!");
    }
}
