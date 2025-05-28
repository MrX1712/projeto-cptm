package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.repository.PassageiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassageiroService {
    @Autowired
    private PassageiroRepository passageiroRepository;
}
