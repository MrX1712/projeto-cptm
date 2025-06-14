package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Estacao;
import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.model.Notificacao;
import br.edu.ibmec.cptm.model.TimeCptm;
import br.edu.ibmec.cptm.service.EstacaoService;
import br.edu.ibmec.cptm.service.LinhaService;
import br.edu.ibmec.cptm.service.NotificacaoService;
import br.edu.ibmec.cptm.service.TimeCptmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cptm+/adm/painel-administrativo/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private TimeCptmService timeCptmService;

    @Autowired
    private EstacaoService estacaoService;

    @Autowired
    private LinhaService linhaService;

    @GetMapping("/listar")
    public String listarNotificacoes(Model model) {
        model.addAttribute("notificacoes", notificacaoService.listar());
        model.addAttribute("timeCptms", timeCptmService.listar());
        model.addAttribute("linhas", linhaService.listar());
        model.addAttribute("estacoes", estacaoService.listar());
        return "notificacoes/listar";
    }

    @GetMapping("/novo")
    public String novaNotificacao(Model model) {
        model.addAttribute("notificacao", new Notificacao());
        model.addAttribute("times", timeCptmService.listar());
        model.addAttribute("linhas", linhaService.listar());
        model.addAttribute("estacoes", estacaoService.listar());
        return "notificacoes/inserir";
    }

    @GetMapping("/editar/{id}")
    public String editarNotificacao(@PathVariable UUID id, Model model) {
        Notificacao notificacao = notificacaoService.buscarPorId(id);
        model.addAttribute("notificacao", notificacao);
        model.addAttribute("times", timeCptmService.listar());
        model.addAttribute("linhas", linhaService.listar());
        model.addAttribute("estacoes", estacaoService.listar());
        return "notificacoes/editar";
    }

    @PostMapping("/salvar")
    public String salvarNotificacao(@ModelAttribute Notificacao notificacao, Model model) {
        TimeCptm timeCptm = timeCptmService.buscarPorId(notificacao.getTimeCptm().getId());
        notificacao.setTimeCptm(timeCptm);

        timeCptm.adicionarNotificacao(notificacao);
        notificacao.setDataEnvio(LocalDateTime.now());

        notificacaoService.salvarOuEditar(notificacao);
        timeCptmService.salvarOuEditar(timeCptm);

        return "redirect:/cptm+/adm/painel-administrativo/notificacoes/listar";
    }

    @PostMapping("/salvar-edicao")
    public String salvarEdicaoNotificacao(@ModelAttribute Notificacao notificacao, Model model) {
        Notificacao notificacaoExistente = notificacaoService.buscarPorId(notificacao.getId());
        notificacao.setDataEnvio(notificacaoExistente.getDataEnvio());

        TimeCptm timeCptm = timeCptmService.buscarPorId(notificacao.getTimeCptm().getId());
        notificacao.setTimeCptm(timeCptm);

        notificacaoService.salvarOuEditar(notificacao);

        return "redirect:/cptm+/adm/painel-administrativo/notificacoes/listar";
    }

    @GetMapping("/remover/{id}")
    public String exibirTelaRemover(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        Notificacao notificacao = notificacaoService.buscarPorId(id);
        if (notificacao == null) {
            redirectAttributes.addFlashAttribute("erro", "Notificação não encontrada.");
            return "redirect:/cptm+/adm/painel-administrativo/notificacoes/listar";
        }
        model.addAttribute("notificacao", notificacao);
        return "notificacoes/remover";
    }

    @PostMapping("/deletar")
    public String confirmarRemocao(@ModelAttribute Notificacao notificacao, RedirectAttributes redirectAttributes) {
        try {
            Notificacao notificacaoExistente = notificacaoService.buscarPorId(notificacao.getId());
            if (notificacaoExistente == null) {
                redirectAttributes.addFlashAttribute("erro", "Notificação não encontrada.");
                return "redirect:/cptm+/adm/painel-administrativo/notificacoes/listar";
            }

            notificacaoService.remover(notificacao.getId());
            redirectAttributes.addFlashAttribute("mensagem", "Notificação '" + notificacaoExistente.getTitulo() + "' excluída com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir a notificação: " + e.getMessage());
        }
        return "redirect:/cptm+/adm/painel-administrativo/notificacoes/listar";
    }

    @GetMapping("/estacoes/{linhaId}")
    @ResponseBody
    public List<Estacao> listarEstacoesPorLinha(@PathVariable UUID linhaId) {
        Linha linha = linhaService.buscarPorId(linhaId);
        return estacaoService.listarPorLinha(linhaId);
    }
}