package br.edu.ibmec.cptm.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_SOLICITACAO_AJUDA", schema = "CPTM")
public class SolicitacaoAjuda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_SOLICITACAO")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ID_LINHA", nullable = false)
    private Linha linha;

    @ManyToOne
    @JoinColumn(name = "ID_ESTACAO", nullable = false)
    private Estacao estacao;

    @ManyToOne
    @JoinColumn(name = "ID_PASSAGEIRO", nullable = false)
    private Passageiro passageiro;

    @Column(name = "TEMA", nullable = false)
    private int tema;

    @Column(name = "COMENTARIO",nullable = false)
    private String comentario;

    @Column(name = "STATUS", nullable = false)
    private boolean status;

    public SolicitacaoAjuda() {
    }

    public SolicitacaoAjuda(UUID id, Estacao estacao, Linha linha, Passageiro passageiro, String comentario, int tema, boolean status) {
        this.id = id;
        this.estacao = estacao;
        this.linha = linha;
        this.passageiro = passageiro;
        this.comentario = comentario;
        this.tema = tema;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Estacao getEstacao() {
        return estacao;
    }

    public void setEstacao(Estacao estacao) {
        this.estacao = estacao;
    }

    public Linha getLinha() {
        return linha;
    }

    public void setLinha(Linha linha) {
        this.linha = linha;
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

    public int getTema() {
        return tema;
    }

    public void setTema(int tema) {
        this.tema = tema;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean statusAjuda) {
        this.status = statusAjuda;
    }
}
