package br.edu.ibmec.cptm.repository;

import br.edu.ibmec.cptm.model.Estacao;
import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.model.SolicitacaoAjuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolicitacaoAjudaRepository extends JpaRepository<SolicitacaoAjuda, UUID> {

    List<SolicitacaoAjuda> findAllByStatus(boolean status);

    List<SolicitacaoAjuda> findAllByLinha(Linha linha);

    List<SolicitacaoAjuda> findAllByEstacao(Estacao estacao);

    List<SolicitacaoAjuda> findAllByPassageiro(Passageiro passageiro);

}
