package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.SolicitacaoAjuda;
import br.edu.ibmec.cptm.service.EstacaoService;
import br.edu.ibmec.cptm.service.LinhaService;
import br.edu.ibmec.cptm.service.PassageiroService;
import br.edu.ibmec.cptm.service.SolicitacaoAjudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/cptm+/adm/painel-administrativo/ajuda")
public class SolicitacaoAjudaController {

    @Autowired
    private SolicitacaoAjudaService solicitacaoAjudaService;

    @Autowired
    private PassageiroService passageiroService;

    @Autowired
    private LinhaService linhaService;

    @Autowired
    private EstacaoService estacaoService;

    @GetMapping("/listar")
    public String listarSolicitacoes(Model model) {
        model.addAttribute("solicitacoes", solicitacaoAjudaService.listar());
        model.addAttribute("passageiros", passageiroService.listar());
        model.addAttribute("linhas", linhaService.listar());
        model.addAttribute("estacoes", estacaoService.listar());
        return "ajuda/listar";
    }

    // === PÁGINA DE CONFIRMAÇÃO DE REMOÇÃO ===
    @GetMapping("/remover/{id}")
    public String confirmarRemocaoSolicitacao(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        SolicitacaoAjuda ajuda = solicitacaoAjudaService.buscarPorId(id);
        if (ajuda == null) {
            redirectAttributes.addFlashAttribute("erro", "Solicitação não encontrada.");
            return "redirect:/cptm+/adm/painel-administrativo/ajuda/listar";
        }
        model.addAttribute("ajuda", ajuda);
        return "ajuda/remover";
    }

    // === DELETAR SOLICITAÇÃO (CONFIRMADO) ===
    @PostMapping("/deletar")
    public String deletarSolicitacao(@RequestParam("id") UUID id, RedirectAttributes redirectAttributes) {
        try {
            SolicitacaoAjuda ajuda = solicitacaoAjudaService.buscarPorId(id);
            if (ajuda != null) {
                solicitacaoAjudaService.remover(id);
                redirectAttributes.addFlashAttribute("mensagem", "Solicitação de '" + ajuda.getPassageiro().getNome() + "' excluída com sucesso.");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Solicitação não encontrada.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir solicitação: " + e.getMessage());
        }
        return "redirect:/cptm+/adm/painel-administrativo/ajuda/listar";
    }

    @PostMapping("/resolver/{id}")
    public String marcarComoResolvido(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            SolicitacaoAjuda ajuda = solicitacaoAjudaService.buscarPorId(id);
            if (ajuda != null) {
                solicitacaoAjudaService.marcarComoResolvido(id);
                redirectAttributes.addFlashAttribute("mensagem", "Solicitação de '" + ajuda.getPassageiro().getNome() + "' marcada como resolvida.");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Solicitação não encontrada.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao marcar solicitação como resolvida: " + e.getMessage());
        }
        return "redirect:/cptm+/adm/painel-administrativo/ajuda/listar";
    }
}