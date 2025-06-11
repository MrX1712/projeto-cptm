package br.edu.ibmec.cptm.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_ESTACAO", schema = "CPTM")
public class Estacao implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "ID_ESTACAO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "NUMERO", nullable = false)
    private Integer numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LINHA_ID", nullable = false)
    @JsonIgnore
    private Linha linha;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    public Estacao() {
    }

    public Estacao(UUID id, String nome, Integer numero, Linha linha, Double latitude, Double longitude) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
        this.linha = linha;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Linha getLinha() {
        return linha;
    }

    public void setLinha(Linha linha) {
        this.linha = linha;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
