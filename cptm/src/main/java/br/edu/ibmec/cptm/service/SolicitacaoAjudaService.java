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

    public void remover(SolicitacaoAjuda solicitacaoAjuda) {
        solicitacaoAjudaRepository.delete(solicitacaoAjuda);
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
}
