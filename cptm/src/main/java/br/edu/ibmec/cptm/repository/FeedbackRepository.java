package br.edu.ibmec.cptm.repository;

import br.edu.ibmec.cptm.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

    List<Feedback> findFeedbackByTipo(String tipo);

    List<Feedback> findFeedbackByNota(int nota);

    List<Feedback> findAllByOrderByDataEnvioDesc();

}
