package br.edu.ibmec.cptm.service;

import br.edu.ibmec.cptm.repository.TimeCptmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeCptmService {
    @Autowired
    private TimeCptmRepository timeCptmRepository;
}
