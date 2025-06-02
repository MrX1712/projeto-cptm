package br.edu.ibmec.cptm.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@DiscriminatorValue("PASSAGEIRO")
public class Passageiro extends Usuario implements Serializable {

    @ManyToMany
    @JoinTable(
            name = "TB_PASSAGEIRO_LINHAS",
            schema = "CPTM",
            joinColumns = @JoinColumn(name = "ID_PASSAGEIRO"),
            inverseJoinColumns = @JoinColumn(name = "ID_LINHA")
    )
    private List<Linha> linhasFavoritas = new ArrayList<>();

    public Passageiro() {
    }

    public Passageiro(List<Linha> linhasFavoritas) {
        this.linhasFavoritas = linhasFavoritas;
    }

    public Passageiro(String email, UUID id, LocalDate dataNascimento, String nome, String cpf, String senha, boolean logado, List<Linha> linhasFavoritas) {
        super(email, id, dataNascimento, nome, cpf, senha, logado);
        this.linhasFavoritas = linhasFavoritas;
    }

    public List<Linha> getLinhasFavoritas() {
        return linhasFavoritas;
    }

    public void setLinhasFavoritas(List<Linha> linhasFavoritas) {
        this.linhasFavoritas = linhasFavoritas;
    }
}
