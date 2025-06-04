package br.edu.ibmec.cptm.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_ESTACAO", schema = "CPTM")
public class Estacao implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "ID_ESTACAO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "NUMERO", nullable = false)
    private int numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LINHA_ID", nullable = false)
    private Linha linha;

    public Estacao() {
    }

    public Estacao(UUID id, String nome, int numero, Linha linha) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
        this.linha = linha;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Linha getLinha() {
        return linha;
    }

    public void setLinha(Linha linha) {
        this.linha = linha;
    }
}
