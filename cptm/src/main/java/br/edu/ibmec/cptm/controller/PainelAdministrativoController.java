package br.edu.ibmec.cptm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cptm+/adm")
public class PainelAdministrativoController {

    @GetMapping("/painel-administrativo")
    public String painelAdministrativo() {
        return "adm/painel-administrativo";
    }
}
