package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.repository.PassageiroRepository;
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

    public List<Passageiro> listar() {
        return passageiroRepository.findAllByOrderByNomeAsc();
    }

    public Passageiro salvarOuEditar(Passageiro passageiro) {
        return passageiroRepository.save(passageiro);
    }

    public void remover(UUID id){
        passageiroRepository.deleteById(id);
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
