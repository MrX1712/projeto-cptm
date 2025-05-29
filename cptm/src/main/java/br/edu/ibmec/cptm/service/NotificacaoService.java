package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.repository.FeedbackRepository;
import br.edu.ibmec.cptm.repository.NotificacaoRepositosy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {
    @Autowired
    private NotificacaoRepositosy notificacaoRepositoy;
}
