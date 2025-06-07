package br.edu.ibmec.cptm.repository;

import br.edu.ibmec.cptm.model.Estacao;
import br.edu.ibmec.cptm.model.Linha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EstacaoRepository extends JpaRepository<Estacao, UUID> {

    Optional<Estacao> findByNome(String nome);

    List<Estacao> findAllByOrderByNumeroAsc();

    List<Estacao> findAllByLinhaId(UUID linhaId);

}
