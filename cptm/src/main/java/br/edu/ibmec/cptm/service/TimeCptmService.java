package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.model.TimeCptm;
import br.edu.ibmec.cptm.repository.TimeCptmRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TimeCptmService {

    @Autowired
    private TimeCptmRepository timeCptmRepository;

    public List<TimeCptm> listar() {
        return timeCptmRepository.findAllByOrderByDataNascimentoAsc();
    }

    public TimeCptm salvarOuEditar(TimeCptm timeCptm) {
        return timeCptmRepository.save(timeCptm);
    }

    public void remover(TimeCptm timeCptm) {
        timeCptmRepository.delete(timeCptm);
    }

    public TimeCptm buscarPorEmail(String email) {
        return timeCptmRepository.findByEmail(email).orElse(null);
    }

    public TimeCptm buscarPorCpf(String cpf) {
        return timeCptmRepository.findByCpf(cpf).orElse(null);
    }
}
