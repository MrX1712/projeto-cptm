package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.model.Feedback;
import br.edu.ibmec.cptm.repository.FeedbackRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public void remover(UUID id) {
        feedbackRepository.deleteById(id);
    }

    public List<Feedback> listarPorTipo(String tipo) {
        return feedbackRepository.findFeedbackByTipo(tipo);
    }

    public List<Feedback> listarPorNota(int nota) {
        return feedbackRepository.findFeedbackByNota(nota);
    }

    public void marcarComoVisto(UUID id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow();
        feedback.setVisto(true);
        feedbackRepository.save(feedback);
    }

}
