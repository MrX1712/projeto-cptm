package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.service.PassageiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/cptm+/login")
public class CadastroController {

    @Autowired
    private PassageiroService passageiroService;

    @GetMapping("/novo")
    public String novoPassageiro(Model model) {
        model.addAttribute("passageiro", new Passageiro());
        return "cptm/cadastro";
    }

    @PostMapping("/salvar")
    public String salvarPassageiro(@ModelAttribute Passageiro passageiro, Model model) {
        Passageiro salvo = passageiroService.salvarOuEditar(passageiro);
        model.addAttribute("passageiro", salvo);
        return "cptm/usuario";
    }

    @GetMapping("/usuario")
    public String exibirPerfil(@RequestParam("id") UUID id, Model model) {
        Passageiro passageiro = passageiroService.buscarPorId(id);

        if (passageiro == null) {
            return "redirect:/cptm+/login";
        }

        model.addAttribute("passageiro", passageiro);
        return "cptm/usuario";
    }
}
