package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.model.SolicitacaoAjuda;
import br.edu.ibmec.cptm.repository.SolicitacaoAjudaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SolicitacaoAjudaService {

    @Autowired
    private SolicitacaoAjudaRepository solicitacaoAjudaRepository;

    public List<SolicitacaoAjuda> listar() {
        return solicitacaoAjudaRepository.findAll();
    }

    public SolicitacaoAjuda salvarOuEditar(SolicitacaoAjuda solicitacaoAjuda) {
        return solicitacaoAjudaRepository.save(solicitacaoAjuda);
    }

    public void remover(UUID id) {
        solicitacaoAjudaRepository.deleteById(id);
    }

    public SolicitacaoAjuda buscarPorId(UUID id) {
        return solicitacaoAjudaRepository.findById(id).orElse(null);
    }

    public void marcarComoResolvido(UUID id) {
        Optional<SolicitacaoAjuda> optional = solicitacaoAjudaRepository.findById(id);
        if (optional.isPresent()) {
            SolicitacaoAjuda solicitacao = optional.get();
            solicitacao.setStatus(true);
            solicitacaoAjudaRepository.save(solicitacao);
        }
    }
}

