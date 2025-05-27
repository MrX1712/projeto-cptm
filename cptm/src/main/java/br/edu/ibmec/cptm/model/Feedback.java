package br.edu.ibmec.cptm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.io.Serializable;

@Entity
@Table(name="TB_FEEDBACK",schema = "CPTM")
public class Feedback implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name="ID_FEEDBACK",nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_PASSAGEIRO", nullable = false)
    private Passageiro passageiro= new Passageiro();

    @Column(name="COMENTARIO",nullable = false)
    private String comentario;

    @Column(name = "NOTA",nullable = false)
    private int nota;

    @Column(name = "DATAENVIO",nullable = false)
    private LocalDateTime dataEnvio;

    public Feedback() {
    }

    public Feedback(UUID id, Passageiro passageiro, String comentario, int nota, LocalDateTime dataEnvio) {
        this.id = id;
        this.passageiro = passageiro;
        this.comentario = comentario;
        this.nota = nota;
        this.dataEnvio = dataEnvio;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Passageiro getPassageiro() {
        return passageiro;
    }

    public void setPassageiro(Passageiro passageiro) {
        this.passageiro = passageiro;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}
