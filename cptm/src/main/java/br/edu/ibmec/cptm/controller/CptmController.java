package br.edu.ibmec.cptm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cptm+")
public class CptmController {

    @RequestMapping("/home")
    public String home() {
        return "cptm/home";
    }

    @RequestMapping("/login")
    public String login() {
        return "cptm/login";
    }

    @RequestMapping("/ajuda")
    public String ajuda() {
        return "cptm/ajuda";
    }

    @RequestMapping("/estacoes")
    public String estacoes() {
        return "cptm/estacoes";
    }

    @RequestMapping("/feedback")
    public String feedback() {
        return "cptm/feedback";
    }

    @RequestMapping("/cadastro")
    public String cadastro() {
        return "cptm/cadastro";
    }

    @RequestMapping("/rastreamento")
    public String rastreamento() {
        return "cptm/rastreamento";
    }

    @RequestMapping("/usuario")
    public String usuario() {
        return "cptm/usuario";
    }
}
