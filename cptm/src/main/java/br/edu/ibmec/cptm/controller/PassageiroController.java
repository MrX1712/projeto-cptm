package br.edu.ibmec.cptm.controller;


import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.service.LinhaService;
import br.edu.ibmec.cptm.service.PassageiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cptm+/adm/painel-administrativo/passageiros")
public class PassageiroController {

    @Autowired
    private PassageiroService passageiroService;

    @Autowired
    private LinhaService linhaService;

    @GetMapping("/listar")
    public String listarPassageiro(Model model) {
        model.addAttribute("passageiros", passageiroService.listar());
        model.addAttribute("linhas", linhaService.listar());
        return "/passageiros/listar";
    }

    @GetMapping("/novo")
    public String novoPassageiro(Model model) {
        model.addAttribute("passageiro", new Passageiro());
        model.addAttribute("linhas", linhaService.listar());
        return "/passageiros/inserir";
    }

    @PostMapping("/salvar")
    public String salvarPassageiro(@ModelAttribute Passageiro passageiro) {
        List<Linha> linhasCompletas = passageiro.getLinhasFavoritas().stream()
                .map(l -> linhaService.buscarPorId(l.getId()))
                .toList();

        passageiro.setLinhasFavoritas(linhasCompletas);
        passageiroService.salvarOuEditar(passageiro);

        return "redirect:/cptm+/adm/painel-administrativo/passageiros/listar";
    }

    @GetMapping("/remover/{id}")
    public String removerPassageiro(@PathVariable UUID id) {
        passageiroService.remover(id);
        return "redirect:/cptm+/adm/painel-administrativo/passageiros/listar";
    }

}
