package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.repository.PassageiroRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PassageiroService {

    @Autowired
    private PassageiroRepository passageiroRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Passageiro> listar() {
        return passageiroRepository.findAllByOrderByNomeAsc();
    }

    public Passageiro salvarOuEditar(Passageiro passageiro) {
        return passageiroRepository.save(passageiro);
    }

    @Transactional
    public void remover(UUID id) {
        try {
            entityManager.createQuery("DELETE FROM Feedback f WHERE f.passageiro.id = :passageiroId")
                    .setParameter("passageiroId", id)
                    .executeUpdate();

            entityManager.createNativeQuery("DELETE FROM cptm.tb_solicitacao_ajuda WHERE id_passageiro = ?")
                    .setParameter(1, id)
                    .executeUpdate();

            passageiroRepository.deleteById(id);

            entityManager.flush();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover passageiro e dados relacionados: " + e.getMessage(), e);
        }
    }

    public Passageiro buscarPorId(UUID id){
        return passageiroRepository.findById(id).orElse(null);
    }

    public Passageiro buscarComLinhasFavoritas(UUID id) {
        return passageiroRepository.buscarComLinhasFavoritas(id).orElse(null);
    }
}