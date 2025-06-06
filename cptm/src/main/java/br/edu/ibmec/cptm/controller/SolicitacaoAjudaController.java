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
        return "/ajuda/listar";
    }

    @GetMapping("/novo")
    public String novaSolicitacao(Model model) {
        model.addAttribute("solicitacaoAjuda", new SolicitacaoAjuda());
        model.addAttribute("passageiros", passageiroService.listar());
        model.addAttribute("linhas", linhaService.listar());
        model.addAttribute("estacoes", estacaoService.listar());
        return "/ajuda/inserir";
    }

    @PostMapping("/salvar")
    public String salvarSolicitacao(@ModelAttribute SolicitacaoAjuda solicitacaoAjuda) {
        solicitacaoAjuda.setStatus(false); // por padrão, não resolvido
        solicitacaoAjudaService.salvarOuEditar(solicitacaoAjuda);
        return "redirect:/cptm+/adm/painel-administrativo/ajuda/listar";
    }

    @PostMapping("/remover/{id}")
    public String removerSolicitacao(@PathVariable UUID id) {
        solicitacaoAjudaService.remover(id);
        return "redirect:/cptm+/adm/painel-administrativo/ajuda/listar";
    }

    @PostMapping("/resolver/{id}")
    public String marcarComoResolvido(@PathVariable UUID id) {
        solicitacaoAjudaService.marcarComoResolvido(id);
        return "redirect:/cptm+/adm/painel-administrativo/ajuda/listar";
    }
}
