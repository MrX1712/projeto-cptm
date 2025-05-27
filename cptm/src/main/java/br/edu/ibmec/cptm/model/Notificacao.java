package br.edu.ibmec.cptm.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="TB_NOTIFICACAO",schema="CPTM")
public class Notificacao implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name="ID_NOTIFICACAO")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;

    @Column(name = "TEXTO",nullable = false)
    private String texto;

    @Column(name="TITULO", nullable = false)
    private String titulo;

    @Column(name="DATAENVIO",nullable = false)
    private LocalDateTime dataEnvio;


    @ManyToOne
    @JoinColumn(name="LINHA_NOTIFICACAO")
    private Linha linhaNotificacao;

    @ManyToOne
    @JoinColumn(name="ESTACAO_NOTIFICACAO")
    private Estacao estacaoNotificacao;

    public Notificacao() {
    }

    public Notificacao(UUID id, String texto, String titulo, LocalDateTime dataEnvio, Linha linhaNotificacao, Estacao estacaoNotificacao) {
        this.id = id;
        this.texto = texto;
        this.titulo = titulo;
        this.dataEnvio = dataEnvio;
        this.linhaNotificacao = linhaNotificacao;
        this.estacaoNotificacao = estacaoNotificacao;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Linha getLinhaNotificacao() {
        return linhaNotificacao;
    }

    public void setLinhaNotificacao(Linha linhaNotificacao) {
        this.linhaNotificacao = linhaNotificacao;
    }

    public Estacao getEstacaoNotificacao() {
        return estacaoNotificacao;
    }

    public void setEstacaoNotificacao(Estacao estacaoNotificacao) {
        this.estacaoNotificacao = estacaoNotificacao;
    }
}
