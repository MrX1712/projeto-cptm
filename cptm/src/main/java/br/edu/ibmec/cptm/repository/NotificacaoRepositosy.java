package br.edu.ibmec.cptm.repository;

import br.edu.ibmec.cptm.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificacaoRepositosy extends JpaRepository<Notificacao, UUID> {

    List<Notificacao> findAllByOrderByDataEnvioDesc();

    List<Notificacao> findByLinhaIdIn(List<UUID> ids);
}
