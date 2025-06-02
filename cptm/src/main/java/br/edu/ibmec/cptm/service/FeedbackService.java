package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.model.Feedback;
import br.edu.ibmec.cptm.repository.FeedbackRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> listar() {
        return feedbackRepository.findAllByOrderByDataEnvioDesc();
    }

    public Feedback salvarOuEditar(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public void remover(Feedback feedback) {
        feedbackRepository.delete(feedback);
    }

    public List<Feedback> listarPorTipo(String tipo) {
        return feedbackRepository.findFeedbackByTipo(tipo);
    }

    public List<Feedback> listarPorNota(int nota) {
        return feedbackRepository.findFeedbackByNota(nota);
    }
}
