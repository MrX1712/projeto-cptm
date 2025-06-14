package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.service.LinhaService;
import br.edu.ibmec.cptm.service.PassageiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "passageiros/listar";
    }

    @GetMapping("/remover/{id}")
    public String exibirTelaRemover(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        Passageiro passageiro = passageiroService.buscarPorId(id);
        if (passageiro == null) {
            redirectAttributes.addFlashAttribute("erro", "Passageiro não encontrado.");
            return "redirect:/cptm+/adm/painel-administrativo/passageiros/listar";
        }
        model.addAttribute("passageiro", passageiro);
        return "passageiros/remover";
    }

    @PostMapping("/deletar")
    public String confirmarRemocao(@ModelAttribute Passageiro passageiro, RedirectAttributes redirectAttributes) {
        try {
            Passageiro passageiroExistente = passageiroService.buscarPorId(passageiro.getId());
            if (passageiroExistente == null) {
                redirectAttributes.addFlashAttribute("erro", "Passageiro não encontrado.");
                return "redirect:/cptm+/adm/painel-administrativo/passageiros/listar";
            }

            passageiroService.remover(passageiro.getId());
            redirectAttributes.addFlashAttribute("mensagem", "Passageiro '" + passageiroExistente.getNome() + "' e seus dados relacionados (solicitações e feedbacks) excluídos com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir o passageiro: " + e.getMessage());
        }
        return "redirect:/cptm+/adm/painel-administrativo/passageiros/listar";
    }
}