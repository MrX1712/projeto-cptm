package br.edu.ibmec.cptm.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_NOTIFICACAO", schema = "CPTM")
public class Notificacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_NOTIFICACAO")
    private UUID id;

    @Column(name = "TITULO", nullable = false)
    private String titulo;

    @Column(name = "TEXTO", nullable = false)
    private String texto;

    @Column(name = "DATA_ENVIO", nullable = false)
    private LocalDateTime dataEnvio;

    @ManyToOne
    @JoinColumn(name = "LINHA_ID")
    private Linha linha;

    @ManyToOne
    @JoinColumn(name = "ESTACAO_ID")
    private Estacao estacao;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private TimeCptm timeCptm;

    public Notificacao() {
    }

    public Notificacao(UUID id, String titulo, String texto, LocalDateTime dataEnvio, Linha linha, Estacao estacao, TimeCptm timeCptm) {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
        this.dataEnvio = dataEnvio;
        this.linha = linha;
        this.estacao = estacao;
        this.timeCptm = timeCptm;
    }

    public TimeCptm getTimeCptm() {
        return timeCptm;
    }

    public void setTimeCptm(TimeCptm timeCptm) {
        this.timeCptm = timeCptm;
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

    public Linha getLinha() {
        return linha;
    }

    public void setLinha(Linha linhaNotificacao) {
        this.linha = linhaNotificacao;
    }

    public Estacao getEstacao() {
        return estacao;
    }

    public void setEstacao(Estacao estacaoNotificacao) {
        this.estacao = estacaoNotificacao;
    }
}
