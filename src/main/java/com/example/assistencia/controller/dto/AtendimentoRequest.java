package com.example.assistencia.controller.dto;

import com.example.assistencia.model.TipoAtendimento;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AtendimentoRequest {
    private Long clienteId;
    private Long tecnicoId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaInicial;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaFinal;

    private TipoAtendimento tipo;
    private String chamadoNumero;
    private String observacoes;

    private List<ServicoItemDTO> servicos;

    // getters/setters
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getTecnicoId() { return tecnicoId; }
    public void setTecnicoId(Long tecnicoId) { this.tecnicoId = tecnicoId; }

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

    public List<ServicoItemDTO> getServicos() { return servicos; }
    public void setServicos(List<ServicoItemDTO> servicos) { this.servicos = servicos; }
}
