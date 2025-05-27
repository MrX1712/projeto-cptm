package br.edu.ibmec.cptm.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="TB_SOLICITACAOAJUDA",schema = "CPTM")
public class SolicitacaoAjuda implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "ID_AJUDA")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    @ManyToOne
    @JoinColumn(name="ESTACAO_AJUDA")
    private Estacao estacao=new Estacao();

    @ManyToOne
    @JoinColumn(name="LINHA_AJUDA")
    private Linha linha=new Linha();

    @OneToOne
    @JoinColumn(name="PASSAGEIRO_AJUDA")
    private Passageiro passageiro=new Passageiro();

    @Column(name="COMENTARIO",nullable=false)
    private String comentario;

    @Column(name = "TEMA",nullable = false)
    private int tema;

    @Column(name = "STATUS_AJUDA",nullable = false)
    private boolean statusAjuda;

    public SolicitacaoAjuda() {
    }

    public SolicitacaoAjuda(UUID id, Estacao estacao, Linha linha, Passageiro passageiro, String comentario, int tema, boolean statusAjuda) {
        this.id = id;
        this.estacao = estacao;
        this.linha = linha;
        this.passageiro = passageiro;
        this.comentario = comentario;
        this.tema = tema;
        this.statusAjuda = statusAjuda;
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

    public boolean isStatusAjuda() {
        return statusAjuda;
    }

    public void setStatusAjuda(boolean statusAjuda) {
        this.statusAjuda = statusAjuda;
    }
}
