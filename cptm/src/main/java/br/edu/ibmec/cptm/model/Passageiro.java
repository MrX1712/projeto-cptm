package br.edu.ibmec.cptm.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="TB_PASSAGEIRO",schema = "CPTM")
public class Passageiro extends Usuario {
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PASSAGEIRO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="LINHASFAVORITAS")
    private List<Linha> linhasFavoritas;

    public Passageiro() {
    }


    public Passageiro(String email, UUID id, LocalDate dataDeNascimento, String nome, String cpf, String senha, boolean logado, UUID id1, List<Linha> linhasFavoritas) {
        super(email, id, dataDeNascimento, nome, cpf, senha, logado);
        this.id = id1;
        this.linhasFavoritas = linhasFavoritas;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public List<Linha> getLinhasFavoritas() {
        return linhasFavoritas;
    }

    public void setLinhasFavoritas(List<Linha> linhasFavoritas) {
        this.linhasFavoritas = linhasFavoritas;
    }
}
