package br.edu.ibmec.cptm.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="TB_USUARIO", schema="CPTM")
public class Usuario implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name="ID_USUARIO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "DataDeNascimento",nullable = false)
    private LocalDate dataDeNascimento;

    @Column(name = "NOME",nullable = false)
    private String nome;

    @Column(name="cpf",nullable = false)
    private String cpf;

    @Column(name="SENHA",nullable = false)
    private String senha;

    @Column(name = "EMAIL",nullable = false)
    private String email;

    @Column(name="LOGADO",nullable = false)
    private boolean logado;

    public Usuario() {
    }

    public Usuario(String email, UUID id, LocalDate dataDeNascimento, String nome, String cpf, String senha, boolean logado) {
        this.email = email;
        this.id = id;
        this.dataDeNascimento = dataDeNascimento;
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.logado = logado;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }
}
