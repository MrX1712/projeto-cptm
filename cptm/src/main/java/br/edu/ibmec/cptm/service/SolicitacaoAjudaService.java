package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.model.Estacao;
import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.model.Passageiro;
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

    public List<SolicitacaoAjuda> listarPorStatus(boolean status) {
        return solicitacaoAjudaRepository.findAllByStatus(status);
    }

    public List<SolicitacaoAjuda> listarPorLinha(Linha linha) {
        return solicitacaoAjudaRepository.findAllByLinha(linha);
    }

    public List<SolicitacaoAjuda> listarPorEstacao(Estacao estacao) {
        return solicitacaoAjudaRepository.findAllByEstacao(estacao);
    }

    public List<SolicitacaoAjuda> listarPorPassageiro(Passageiro passageiro) {
        return solicitacaoAjudaRepository.findAllByPassageiro(passageiro);
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

