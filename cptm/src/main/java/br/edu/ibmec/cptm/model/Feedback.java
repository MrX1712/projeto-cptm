package br.edu.ibmec.cptm.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_FEEDBACK", schema = "CPTM")
public class Feedback implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_FEEDBACK", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PASSAGEIRO_ID", nullable = false)
    private Passageiro passageiro;

    @Column(name="TIPO", nullable = false)
    private String tipo;

    @Column(name = "COMENTARIO", nullable = false)
    private String comentario;

    @Column(name = "NOTA", nullable = false)
    private int nota;

    @Column(name = "DATA_ENVIO", nullable = false)
    private LocalDateTime dataEnvio;

    public Feedback() {}

    public Feedback(UUID id, Passageiro passageiro, String tipo, String comentario, int nota, LocalDateTime dataEnvio) {
        this.id = id;
        this.passageiro = passageiro;
        this.tipo = tipo;
        this.comentario = comentario;
        this.nota = nota;
        this.dataEnvio = dataEnvio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
