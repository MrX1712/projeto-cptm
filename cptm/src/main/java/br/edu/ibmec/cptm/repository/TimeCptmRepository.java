package br.edu.ibmec.cptm.repository;

import br.edu.ibmec.cptm.model.TimeCptm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TimeCptmRepository extends JpaRepository<TimeCptm, UUID> {

    List<TimeCptm> findAllByOrderByDataNascimentoAsc();
}
