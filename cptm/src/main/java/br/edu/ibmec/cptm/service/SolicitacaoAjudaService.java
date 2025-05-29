package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.repository.SolicitacaoAjudaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitacaoAjudaService {
    @Autowired
    private SolicitacaoAjudaRepository solicitacaoAjudaRepository;
}
