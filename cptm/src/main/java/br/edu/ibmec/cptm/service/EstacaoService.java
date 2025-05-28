package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.repository.EstacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EstacaoService {
    @Autowired
    private EstacaoRepository estacaoRepository;
}
