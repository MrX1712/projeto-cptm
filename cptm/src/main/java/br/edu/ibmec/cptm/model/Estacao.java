package br.edu.ibmec.cptm.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="TB_ESTACAO",schema = "CPTM")
public class Estacao implements Serializable {

    @Id
    @Basic(optional=false)
    @Column(name = "ID_ESTACAO")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;


    @Column(name = "NOMEESTACAO")
    private String nomeEstacao;

    public Estacao() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeEstacao() {
        return nomeEstacao;
    }

    public void setNomeEstacao(String nomeEstacao) {
        this.nomeEstacao = nomeEstacao;
    }

    public Estacao(UUID id, String nomeEstacao) {
        this.id = id;
        this.nomeEstacao = nomeEstacao;


    }
}
