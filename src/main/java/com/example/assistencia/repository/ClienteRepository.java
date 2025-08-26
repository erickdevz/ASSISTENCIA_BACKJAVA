package com.example.assistencia.repository;

import com.example.assistencia.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Você pode adicionar métodos personalizados aqui no futuro
}
