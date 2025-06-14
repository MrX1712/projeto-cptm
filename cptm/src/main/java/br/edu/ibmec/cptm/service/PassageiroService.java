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
            // Primeiro, remove todos os feedbacks relacionados ao passageiro
            entityManager.createQuery("DELETE FROM Feedback f WHERE f.passageiro.id = :passageiroId")
                    .setParameter("passageiroId", id)
                    .executeUpdate();

            // Remove todas as solicitações de ajuda relacionadas ao passageiro
            entityManager.createNativeQuery("DELETE FROM cptm.tb_solicitacao_ajuda WHERE id_passageiro = ?")
                    .setParameter(1, id)
                    .executeUpdate();

            // Por último, remove o passageiro (que automaticamente remove as associações com linhas favoritas)
            passageiroRepository.deleteById(id);

            // Força a sincronização com o banco
            entityManager.flush();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover passageiro e dados relacionados: " + e.getMessage(), e);
        }
    }

    public Passageiro buscarPorId(UUID id){
        return passageiroRepository.findById(id).orElse(null);
    }

    public Passageiro buscarPorCpf(String cpf) {
        return passageiroRepository.findByCpf(cpf).orElse(null);
    }

    public Passageiro buscarPorEmail(String email) {
        return passageiroRepository.findByEmail(email).orElse(null);
    }

    public List<Passageiro> listarPorDataDeNascimento() {
        return passageiroRepository.findAllByOrderByDataNascimentoAsc();
    }

    public Passageiro buscarComLinhasFavoritas(UUID id) {
        return passageiroRepository.buscarComLinhasFavoritas(id).orElse(null);
    }
}