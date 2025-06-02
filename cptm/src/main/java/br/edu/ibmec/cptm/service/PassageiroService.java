package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.repository.PassageiroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PassageiroService {

    @Autowired
    private PassageiroRepository passageiroRepository;

    public List<Passageiro> listar() {
        return passageiroRepository.findAllByOrderByNomeAsc();
    }

    public Passageiro salvarOuEditar(Passageiro passageiro) {
        return passageiroRepository.save(passageiro);
    }

    public void remover(Passageiro passageiro) {
        passageiroRepository.delete(passageiro);
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
}
