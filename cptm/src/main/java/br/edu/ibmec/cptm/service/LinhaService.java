package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.repository.LinhaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LinhaService {

    @Autowired
    private LinhaRepository linhaRepository;

    public List<Linha> listar() {
        return linhaRepository.findAllByOrderByNomeAsc();
    }

    public Linha salvarOuEditar(Linha linha) {
        return linhaRepository.save(linha);
    }

    public void remover(UUID linhaId) {
        linhaRepository.deleteById(linhaId);
    }

    public Linha buscarPorNome(String nome) {
        return linhaRepository.findByNome(nome).orElse(null);
    }

    public Linha buscarPorId(UUID id) {
        return linhaRepository.findById(id).orElse(null);
    }
}
