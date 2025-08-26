package com.example.assistencia.controller;

import com.example.assistencia.controller.dto.AtendimentoRequest;
import com.example.assistencia.controller.dto.ServicoItemDTO;
import com.example.assistencia.model.*;
import com.example.assistencia.repository.AtendimentoRepository;
import com.example.assistencia.repository.ClienteRepository;
import com.example.assistencia.repository.TecnicoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {

    private final AtendimentoRepository atendimentoRepo;
    private final ClienteRepository clienteRepo;
    private final TecnicoRepository tecnicoRepo;

    public AtendimentoController(AtendimentoRepository atendimentoRepo,
                                 ClienteRepository clienteRepo,
                                 TecnicoRepository tecnicoRepo) {
        this.atendimentoRepo = atendimentoRepo;
        this.clienteRepo = clienteRepo;
        this.tecnicoRepo = tecnicoRepo;
    }

    @GetMapping
    public List<Atendimento> listar() { return atendimentoRepo.findAll(); }

    @GetMapping("/{id}")
    public Atendimento obter(@PathVariable Long id) {
        return atendimentoRepo.findById(id).orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Atendimento criar(@Valid @RequestBody AtendimentoRequest req) {

        Cliente cliente = clienteRepo.findById(req.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Tecnico tecnico = tecnicoRepo.findById(req.getTecnicoId())
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado"));

        Atendimento a = new Atendimento();
        a.setCliente(cliente);
        a.setTecnico(tecnico);
        a.setData(req.getData());
        a.setHoraInicial(req.getHoraInicial());
        a.setHoraFinal(req.getHoraFinal());
        a.setTipo(req.getTipo());
        a.setChamadoNumero(req.getChamadoNumero());
        a.setObservacoes(req.getObservacoes());

        if (req.getServicos() != null) {
            for (ServicoItemDTO s : req.getServicos()) {
                ServicoRealizado sr = new ServicoRealizado();
                sr.setTipo(s.getTipo());
                sr.setDescricao(s.getDescricao());
                a.addServico(sr);
            }
        }
        return atendimentoRepo.save(a);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) { atendimentoRepo.deleteById(id); }
}
