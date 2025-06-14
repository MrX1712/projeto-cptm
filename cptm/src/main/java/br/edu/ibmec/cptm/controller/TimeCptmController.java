package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.TimeCptm;
import br.edu.ibmec.cptm.service.TimeCptmService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.UUID;

@Controller
@RequestMapping("/cptm+/adm/painel-administrativo/time-cptm")
public class TimeCptmController {

    @Autowired
    private TimeCptmService timeCptmService;

    @GetMapping("/listar")
    public String listarTimeCptm(Model model) {
        model.addAttribute("times", timeCptmService.listar());
        return "time-cptm/listar";
    }

    @GetMapping("/novo")
    public String novoTimeCptm(Model model) {
        model.addAttribute("timeCptm", new TimeCptm());
        return "time-cptm/inserir";
    }

    @PostMapping("/salvar")
    public String salvarTimeCptm(TimeCptm timeCptm) {
        timeCptmService.salvarOuEditar(timeCptm);
        return "redirect:/cptm+/adm/painel-administrativo/time-cptm/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarTimeCptm(@PathVariable UUID id, Model model) {
        TimeCptm timeCptm = timeCptmService.buscarPorId(id);
        model.addAttribute("timeCptm", timeCptm);
        return "time-cptm/editar";
    }

    @GetMapping("/remover/{id}")
    public String exibirTelaRemover(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        TimeCptm timeCptm = timeCptmService.buscarPorId(id);
        if (timeCptm == null) {
            redirectAttributes.addFlashAttribute("erro", "Funcionário não encontrado.");
            return "redirect:/cptm+/adm/painel-administrativo/time-cptm/listar";
        }
        model.addAttribute("timeCptm", timeCptm);
        return "time-cptm/remover";
    }

    @PostMapping("/deletar")
    public String confirmarRemocao(@ModelAttribute TimeCptm timeCptm, RedirectAttributes redirectAttributes) {
        try {
            TimeCptm timeCptmExistente = timeCptmService.buscarPorId(timeCptm.getId());
            if (timeCptmExistente == null) {
                redirectAttributes.addFlashAttribute("erro", "Funcionário não encontrado.");
                return "redirect:/cptm+/adm/painel-administrativo/time-cptm/listar";
            }

            // Remove o time e todas as notificações relacionadas
            timeCptmService.remover(timeCptm.getId());
            redirectAttributes.addFlashAttribute("mensagem", "Funcionário '" + timeCptmExistente.getNome() + "' e suas notificações excluídos com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir o funcionário: " + e.getMessage());
        }
        return "redirect:/cptm+/adm/painel-administrativo/time-cptm/listar";
    }

    @GetMapping("/notificacoes/{id}")
    public String listarNotificacoes(@PathVariable UUID id, Model model) {
        TimeCptm timeCptm = timeCptmService.buscarPorId(id);
        if (timeCptm != null) {
            model.addAttribute("notificacoes", timeCptm.getNotificacoesEnviadas());
        } else {
            model.addAttribute("notificacoes", new ArrayList<>());
        }
        return "notificacoes/listar";
    }
}