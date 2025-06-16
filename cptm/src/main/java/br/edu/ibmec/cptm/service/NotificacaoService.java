package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.model.Notificacao;
import br.edu.ibmec.cptm.repository.NotificacaoRepositosy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class NotificacaoService {

    @Autowired
    private NotificacaoRepositosy notificacaoRepositoy;

    public List<Notificacao> listar() {
        return notificacaoRepositoy.findAllByOrderByDataEnvioDesc();
    }

    public Notificacao salvarOuEditar(Notificacao notificacao) {
        return notificacaoRepositoy.save(notificacao);
    }

    public void remover(UUID id) {
        notificacaoRepositoy.deleteById(id);
    }

    public Notificacao buscarPorId(UUID id) {
        return notificacaoRepositoy.findById(id).orElse(null);
    }

    public List<Notificacao> buscarLinhas(List<UUID> idsLinhas) {
        return notificacaoRepositoy.findByLinhaIdIn(idsLinhas);
    }
}
