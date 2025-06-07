package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.model.Estacao;
import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.repository.EstacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstacaoService {

    @Autowired
    private EstacaoRepository estacaoRepository;

    public List<Estacao> listar() {
        return estacaoRepository.findAllByOrderByNumeroAsc();
    }

    public Estacao salvarOuEditar(Estacao estacao) {
        return estacaoRepository.save(estacao);
    }

    public Estacao buscarPorNome(String nome) {
        return estacaoRepository.findByNome(nome).orElse(null);
    }

    public void remover(Estacao estacao) {
        estacaoRepository.delete(estacao);
    }

    public List<Estacao> listarPorLinha(UUID linhaId) {
        return  estacaoRepository.findAllByLinhaId(linhaId);
    }

    public Estacao buscarPorId(UUID id) {
        return estacaoRepository.findById(id).orElse(null);
    }
}
