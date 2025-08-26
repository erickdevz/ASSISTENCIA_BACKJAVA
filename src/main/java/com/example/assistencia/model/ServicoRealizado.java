package com.example.assistencia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "servico_realizado")
public class ServicoRealizado {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoServico tipo;

    // descrição livre (ex: "Backup + validar BD", "Gerar/Validar SPED", etc.)
    @Column(length = 1000)
    private String descricao;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Atendimento getAtendimento() { return atendimento; }
    public void setAtendimento(Atendimento atendimento) { this.atendimento = atendimento; }

    public TipoServico getTipo() { return tipo; }
    public void setTipo(TipoServico tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
