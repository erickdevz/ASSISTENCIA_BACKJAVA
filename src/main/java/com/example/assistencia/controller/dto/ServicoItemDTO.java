package com.example.assistencia.controller.dto;

import com.example.assistencia.model.TipoServico;

public class ServicoItemDTO {
    private TipoServico tipo;
    private String descricao;

    public TipoServico getTipo() { return tipo; }
    public void setTipo(TipoServico tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
