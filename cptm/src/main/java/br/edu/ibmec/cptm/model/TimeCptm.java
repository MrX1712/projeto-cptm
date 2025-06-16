package br.edu.ibmec.cptm.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@DiscriminatorValue("TIME_CPTM")
public class TimeCptm extends Usuario implements Serializable {

    @OneToMany(mappedBy = "timeCptm", cascade = CascadeType.ALL)
    private List<Notificacao> notificacoesEnviadas;

    public TimeCptm() {
    }

    public TimeCptm(List<Notificacao> notificacoesEnviadas) {
        this.notificacoesEnviadas = notificacoesEnviadas;
    }

    public TimeCptm(String email, UUID id, LocalDate dataDeNascimento, String nome, String cpf, String senha, boolean logado, List<Notificacao> notificacoesEnviadas) {
        super(email, id, dataDeNascimento, nome, cpf, senha, logado);
        this.notificacoesEnviadas = notificacoesEnviadas;
    }

    public void adicionarNotificacao(Notificacao notificacao) {
        notificacoesEnviadas.add(notificacao);
    }

    public List<Notificacao> getNotificacoesEnviadas() {
        return notificacoesEnviadas;
    }

    public void setNotificacoesEnviadas(List<Notificacao> noticicacaoEnviada) {
        this.notificacoesEnviadas = noticicacaoEnviada;
    }
}
