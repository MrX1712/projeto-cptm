package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.repository.LinhaRepository;
import br.edu.ibmec.cptm.service.LinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cptm+/adm/painel-administrativo/linhas")
public class LinhaController {

    @Autowired
    private LinhaRepository linhaRepository;

    @Autowired
    private LinhaService linhaService;

    @GetMapping
    public String listarLinhas(Model model) {
        List<Linha> linhas = linhaRepository.findAll();
        model.addAttribute("linhas", linhas);
        return "linhas/listar";
    }

    @GetMapping("/novo")
    public String novaLinha(Model model) {
        model.addAttribute("linha", new Linha());
        return "linhas/inserir";
    }

    @GetMapping("/editar/{id}")
    public String editarLinha(@PathVariable UUID id, Model model) {
        Linha linha = linhaRepository.findById(id).orElse(new Linha());
        model.addAttribute("linha", linha);
        return "linhas/editar";
    }

    @PostMapping("/salvar")
    public String salvarLinha(@ModelAttribute Linha linha) {
        if (linha.getId() != null) {
            Linha existente = linhaService.buscarPorId(linha.getId());
            if (existente != null) {
                if (linha.getEstacoes() == null || linha.getEstacoes().isEmpty()) {
                    linha.setEstacoes(existente.getEstacoes());
                }
            }
        }
        linhaService.salvarOuEditar(linha);
        return "redirect:/cptm+/adm/painel-administrativo/linhas";
    }

    @GetMapping("/remover/{id}")
    public String exibirTelaRemover(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        Linha linha = linhaService.buscarPorId(id);
        if (linha == null) {
            redirectAttributes.addFlashAttribute("erro", "Linha não encontrada.");
            return "redirect:/cptm+/adm/painel-administrativo/linhas";
        }
        model.addAttribute("linha", linha);
        return "linhas/remover";
    }

    @PostMapping("/deletar")
    public String confirmarRemocao(@RequestParam("id") UUID id, RedirectAttributes redirectAttributes) {
        try {
            Linha linha = linhaService.buscarPorId(id);
            if (linha == null) {
                redirectAttributes.addFlashAttribute("erro", "Linha não encontrada.");
                return "redirect:/cptm+/adm/painel-administrativo/linhas";
            }

            // Remove a linha e todos os objetos relacionados
            linhaService.remover(id);
            redirectAttributes.addFlashAttribute("mensagem", "Linha '" + linha.getNome() + "' excluída com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir a linha: " + e.getMessage());
        }
        return "redirect:/cptm+/adm/painel-administrativo/linhas";
    }

    @GetMapping("/estacoes/{id}")
    public String listarEstacoesPorLinha(@PathVariable UUID id) {
        return "redirect:/cptm+/adm/painel-administrativo/estacoes?linhaId=" + id.toString();
    }
}