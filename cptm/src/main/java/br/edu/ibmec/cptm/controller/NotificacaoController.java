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
        return "/notificacoes/listar";
    }

    @GetMapping("/novo")
    public String novaNotificacao(Model model) {
        model.addAttribute("notificacao", new Notificacao());
        model.addAttribute("times", timeCptmService.listar());
        model.addAttribute("linhas", linhaService.listar());
        model.addAttribute("estacoes", estacaoService.listar()); // Inicialmente sem filtro
        return "/notificacoes/inserir";
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

    @GetMapping("/remover/{id}")
    public String removerNotificacao(@PathVariable UUID id) {
        notificacaoService.remover(id);
        return "redirect:/cptm+/adm/painel-administrativo/notificacoes/listar";
    }

    @GetMapping("/estacoes/{linhaId}")
    @ResponseBody
    public List<Estacao> listarEstacoesPorLinha(@PathVariable UUID linhaId) {
        Linha linha = linhaService.buscarPorId(linhaId);
        return estacaoService.listarPorLinha(linha);
    }

}
