package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.repository.LinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinhaService {
    @Autowired
    private LinhaRepository linhaRepository;
}
