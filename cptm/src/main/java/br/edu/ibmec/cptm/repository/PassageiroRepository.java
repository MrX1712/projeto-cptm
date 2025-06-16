package br.edu.ibmec.cptm.repository;

import br.edu.ibmec.cptm.model.Passageiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PassageiroRepository extends JpaRepository<Passageiro, UUID> {

    List<Passageiro> findAllByOrderByNomeAsc();

    @Query("SELECT p FROM Passageiro p LEFT JOIN FETCH p.linhasFavoritas WHERE p.id = :id")
    Optional<Passageiro> buscarComLinhasFavoritas(@Param("id") UUID id);
}
