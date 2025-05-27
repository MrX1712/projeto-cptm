package br.edu.ibmec.cptm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="TB_TIMECPTM",schema="CPTM")
public class TimeCptm extends Usuario{

    @JoinColumn(name="NOTIFICACAO_TIME")
    private List<Notificacao> noticicacaoEnviada;

    public TimeCptm(String email, UUID id, LocalDate dataDeNascimento, String nome, String cpf, String senha, boolean logado, List<Notificacao> noticicacaoEnviada) {
        super(email, id, dataDeNascimento, nome, cpf, senha, logado);
        this.noticicacaoEnviada = noticicacaoEnviada;
    }

    public List<Notificacao> getNoticicacaoEnviada() {
        return noticicacaoEnviada;
    }

    public void setNoticicacaoEnviada(List<Notificacao> noticicacaoEnviada) {
        this.noticicacaoEnviada = noticicacaoEnviada;
    }
}
