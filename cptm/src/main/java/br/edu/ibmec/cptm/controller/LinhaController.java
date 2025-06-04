package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.repository.LinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cptm+/adm/painel-administrativo/linhas")
public class LinhaController {

    @Autowired
    private LinhaRepository linhaRepository;

    @GetMapping
    public String listarLinhas(Model model) {
        List<Linha> linhas = linhaRepository.findAll();
        model.addAttribute("linhas", linhas);
        return "linhas/listar";
    }

    @GetMapping("/novo")
    public String novaLinha(Model model) {
        model.addAttribute("linha", new Linha());
        return "linhas/editar";  // formulario para criar/editar linha
    }

    @GetMapping("/editar/{id}")
    public String editarLinha(@PathVariable UUID id, Model model) {
        Linha linha = linhaRepository.findById(id).orElse(new Linha());
        model.addAttribute("linha", linha);
        return "linhas/editar";
    }

    @PostMapping("/salvar")
    public String salvarLinha(@ModelAttribute Linha linha) {
        linhaRepository.save(linha);
        return "redirect:/cptm+/adm/painel-administrativo/linhas";
    }

    @GetMapping("/excluir/{id}")
    public String excluirLinha(@PathVariable UUID id) {
        linhaRepository.deleteById(id);
        return "redirect:/cptm+/adm/painel-administrativo/linhas";
    }

    @GetMapping("/estacoes/{id}")
    public String listarEstacoesPorLinha(@PathVariable UUID id) {
        return "redirect:/cptm+/adm/painel-administrativo/estacoes?linhaId=" + id.toString();
    }
}
