package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Estacao;
import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.service.EstacaoService;
import br.edu.ibmec.cptm.service.LinhaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cptm+/rastreamento")
public class RastreamentoController {

    @Autowired
    private LinhaService linhaService;

    @Autowired
    private EstacaoService estacaoService;

    @GetMapping
    public String mostrarRastreamento(Model model, HttpSession session) {
        List<Linha> linhas = linhaService.listar();
        List<Estacao> estacoes = estacaoService.listar();
        boolean logado = session.getAttribute("passageiro") != null;

        model.addAttribute("linhas", linhas);
        model.addAttribute("estacoes", estacoes);
        model.addAttribute("logado", logado);
        return "cptm/rastreamento";
    }

    @GetMapping("/estacoes")
    @ResponseBody
    public List<Estacao> buscarEstacoesPorLinha(@RequestParam UUID idLinha) {
        return estacaoService.listarPorLinha(idLinha);
    }
}
