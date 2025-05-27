package br.edu.ibmec.cptm.model;

import br.edu.ibmec.cptm.model.Estacao;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name="TB_LINHA",schema="CPTM")
public class Linha implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "ID_LINHA")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JoinColumn(name="LINHA_ESTACAO")
    @OneToMany
    private List<Estacao> estacao;

    @Column(name="NOME_ESTACAO",nullable = false)
    private String nome;

    public Linha() {
    }

    public Linha(UUID id, List<Estacao> estacao, String nome) {
        this.id = id;
        this.estacao = estacao;
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Estacao> getEstacao() {
        return estacao;
    }

    public void setEstacao(List<Estacao> estacao) {
        this.estacao = estacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
