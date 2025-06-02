package br.edu.ibmec.cptm.repository;

import br.edu.ibmec.cptm.model.Linha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinhaRepository extends JpaRepository<Linha, UUID> {

    Optional<Linha> findByNome(String nome);

    List<Linha> findAllByOrderByNomeAsc();
}
