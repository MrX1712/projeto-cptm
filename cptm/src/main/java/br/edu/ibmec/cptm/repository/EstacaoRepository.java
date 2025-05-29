package br.edu.ibmec.cptm.repository;

import br.edu.ibmec.cptm.model.Estacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EstacaoRepository extends JpaRepository<Estacao, UUID> {

}
