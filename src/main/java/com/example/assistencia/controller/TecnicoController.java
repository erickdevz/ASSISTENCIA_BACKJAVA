package com.example.assistencia.controller;

import com.example.assistencia.model.Tecnico;
import com.example.assistencia.repository.TecnicoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoController {

    private final TecnicoRepository repo;

    public TecnicoController(TecnicoRepository repo) { this.repo = repo; }

    @GetMapping public List<Tecnico> listar() { return repo.findAll(); }

    @GetMapping("/{id}")
    public Tecnico obter(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Técnico não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tecnico criar(@Valid @RequestBody Tecnico t) { t.setId(null); return repo.save(t); }

    @PutMapping("/{id}")
    public Tecnico atualizar(@PathVariable Long id, @Valid @RequestBody Tecnico t) {
        Tecnico ex = repo.findById(id).orElseThrow(() -> new RuntimeException("Técnico não encontrado"));
        ex.setNome(t.getNome());
        ex.setCpfCnpj(t.getCpfCnpj());
        ex.setTelefone(t.getTelefone());
        ex.setEndereco(t.getEndereco());
        return repo.save(ex);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) { repo.deleteById(id); }
}
