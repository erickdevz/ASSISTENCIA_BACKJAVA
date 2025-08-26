package com.example.assistencia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "atendimento")
public class Atendimento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(optional = false) @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaInicial;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaFinal;

    @Enumerated(EnumType.STRING)
    private TipoAtendimento tipo;

    private String chamadoNumero;
    private String observacoes;

    @OneToMany(mappedBy = "atendimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoRealizado> servicos = new ArrayList<>();

    // helpers
    public void addServico(ServicoRealizado s){
        s.setAtendimento(this);
        this.servicos.add(s);
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Tecnico getTecnico() { return tecnico; }
    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public LocalTime getHoraInicial() { return horaInicial; }
    public void setHoraInicial(LocalTime horaInicial) { this.horaInicial = horaInicial; }

    public LocalTime getHoraFinal() { return horaFinal; }
    public void setHoraFinal(LocalTime horaFinal) { this.horaFinal = horaFinal; }

    public TipoAtendimento getTipo() { return tipo; }
    public void setTipo(TipoAtendimento tipo) { this.tipo = tipo; }

    public String getChamadoNumero() { return chamadoNumero; }
    public void setChamadoNumero(String chamadoNumero) { this.chamadoNumero = chamadoNumero; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public List<ServicoRealizado> getServicos() { return servicos; }
    public void setServicos(List<ServicoRealizado> servicos) { this.servicos = servicos; }
}
