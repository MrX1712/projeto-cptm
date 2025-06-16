package br.edu.ibmec.cptm.repository;

import br.edu.ibmec.cptm.model.SolicitacaoAjuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SolicitacaoAjudaRepository extends JpaRepository<SolicitacaoAjuda, UUID> {

}
