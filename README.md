# 🚀 Assistência Técnica – Spring Boot + PostgreSQL

API REST em **Java 17** com **Spring Boot** e **PostgreSQL**, criada **para fins de portfólio e aprendizagem**.
Permite **cadastrar e consultar clientes** (CRUD básico), usando **Spring Data JPA/Hibernate** e **Gradle**.

---

## 🧰 Stack

* Java 17 • Spring Boot 3.5.x
* Spring Web • Spring Data JPA • Validation
* PostgreSQL 14+ • HikariCP
* Gradle • Insomnia/Postman

---

## 📦 build.gradle

```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.5.5'
    id 'io.spring.dependency-management' version '1.1.7'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'
description = 'API Assistência Técnica (portfólio/aprendizagem)'

java {
    toolchain { languageVersion = JavaLanguageVersion.of(17) }
}

repositories { mavenCentral() }

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    runtimeOnly 'org.postgresql:postgresql'

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

tasks.named('test') { useJUnitPlatform() }
```

---

## ⚙️ `src/main/resources/application.properties`

```properties
spring.application.name=assistencia

# Ajuste usuário/senha conforme seu ambiente
spring.datasource.url=jdbc:postgresql://localhost:5432/assistencia
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
# Dialect do Postgres é detectado automaticamente no Hibernate 6+/Spring Boot 3+
```

> Crie o banco antes:

```sql
CREATE DATABASE assistencia;
```

---

## 🗂️ Estrutura

```
src/main/java/com/example/assistencia
├─ AssistenciaApplication.java
├─ model/Cliente.java
├─ repository/ClienteRepository.java
├─ controller/ClienteController.java
└─ DatabaseSeeder.java   (opcional: popular dados iniciais)
```

---

## ▶️ Classe principal

`src/main/java/com/example/assistencia/AssistenciaApplication.java`

```java
package com.example.assistencia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssistenciaApplication {
    public static void main(String[] args) {
        SpringApplication.run(AssistenciaApplication.class, args);
    }
}
```

---

## 🧱 Model (Entidade)

`src/main/java/com/example/assistencia/model/Cliente.java`

```java
package com.example.assistencia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @Column(unique = true)
    private String cpfCnpj;

    @Email
    private String email;

    private String telefone;
    private String cidade;
    private String estado;

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpfCnpj() { return cpfCnpj; }
    public void setCpfCnpj(String cpfCnpj) { this.cpfCnpj = cpfCnpj; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
```

---

## 🗃️ Repository

`src/main/java/com/example/assistencia/repository/ClienteRepository.java`

```java
package com.example.assistencia.repository;

import com.example.assistencia.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
```

---

## 🌐 Controller (POST/GET – pronto para testes)

`src/main/java/com/example/assistencia/controller/ClienteController.java`

```java
package com.example.assistencia.controller;

import com.example.assistencia.model.Cliente;
import com.example.assistencia.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // POST - criar cliente
    @PostMapping
    public Cliente criar(@Valid @RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // GET - listar todos
    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    // GET - buscar por ID
    @GetMapping("/{id}")
    public Optional<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteRepository.findById(id);
    }

    // (Opcional) PUT - atualizar
    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @Valid @RequestBody Cliente novo) {
        return clienteRepository.findById(id)
                .map(c -> {
                    c.setNome(novo.getNome());
                    c.setCpfCnpj(novo.getCpfCnpj());
                    c.setEmail(novo.getEmail());
                    c.setTelefone(novo.getTelefone());
                    c.setCidade(novo.getCidade());
                    c.setEstado(novo.getEstado());
                    return clienteRepository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    // (Opcional) DELETE - remover
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        clienteRepository.deleteById(id);
    }
}
```

---

## 🌱 Seeder (opcional)

`src/main/java/com/example/assistencia/DatabaseSeeder.java`

```java
package com.example.assistencia;

import com.example.assistencia.model.Cliente;
import com.example.assistencia.repository.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final ClienteRepository clienteRepository;

    public DatabaseSeeder(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) {
        if (clienteRepository.count() == 0) {
            Cliente c1 = new Cliente();
            c1.setNome("João da Silva");
            c1.setCpfCnpj("12345678900");
            c1.setEmail("joao@email.com");
            c1.setTelefone("31999998888");
            c1.setCidade("Belo Horizonte");
            c1.setEstado("MG");
            clienteRepository.save(c1);

            Cliente c2 = new Cliente();
            c2.setNome("Maria Oliveira");
            c2.setCpfCnpj("98765432100");
            c2.setEmail("maria@email.com");
            c2.setTelefone("31888887777");
            c2.setCidade("Belo Horizonte");
            c2.setEstado("MG");
            clienteRepository.save(c2);

            System.out.println("Clientes iniciais salvos no banco!");
        }
    }
}
```

---

## 🧪 Teste rápido (Insomnia/Postman)

### POST `/clientes`

Body (JSON):

```json
{
  "nome": "João da Silva",
  "cpfCnpj": "12345678900",
  "email": "joao@email.com",
  "telefone": "31999998888",
  "cidade": "Belo Horizonte",
  "estado": "MG"
}
```

### GET `/clientes`

Retorna a lista de clientes.

### GET `/clientes/{id}`

Retorna um cliente específico.

### cURL (opcional)

```bash
curl -X POST http://localhost:8080/clientes \
  -H "Content-Type: application/json" \
  -d '{ "nome":"João da Silva","cpfCnpj":"12345678900",
        "email":"joao@email.com","telefone":"31999998888",
        "cidade":"Belo Horizonte","estado":"MG" }'

curl http://localhost:8080/clientes
```

---

## ▶️ Executando

```bash
# Windows PowerShell (com Gradle wrapper)
.\gradlew bootRun

# macOS/Linux
./gradlew bootRun
```

Aplicação sobe em: `http://localhost:8080`

---

## 🎯 Objetivo

Projeto **para portfólio e aprendizagem**, praticando:

* Integração Spring Boot + PostgreSQL
* JPA/Hibernate e mapeamento de entidades
* Estrutura REST (Controllers/Repositories)
* Testes de API com Insomnia/Postman

---

## 👤 Autores

**Erick Xavier**, **Raienny Firmino**, **Pedro Queiroz**, **Ana Luiza Vidigal** • Projeto acadêmico/experimental (não destinado a produção).
