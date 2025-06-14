package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.SolicitacaoAjuda;
import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.service.EstacaoService;
import br.edu.ibmec.cptm.service.LinhaService;
import br.edu.ibmec.cptm.service.SolicitacaoAjudaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cptm+/ajuda")
public class HomeSolicitacaoAjudaController {

    @Autowired
    private LinhaService linhaService;

    @Autowired
    private EstacaoService estacaoService;

    @Autowired
    private SolicitacaoAjudaService solicitacaoAjudaService;

    @GetMapping
    public String exibirFormularioAjuda(HttpSession session, Model model) {
        Passageiro passageiro = (Passageiro) session.getAttribute("passageiroLogado");

        boolean logado = passageiro != null;
        model.addAttribute("logado", logado);

        if (logado) {
            model.addAttribute("passageiroId", passageiro.getId());
            model.addAttribute("passageiroNome", passageiro.getNome());
        }

        model.addAttribute("solicitacao", new SolicitacaoAjuda());
        model.addAttribute("linhas", linhaService.listar());
        model.addAttribute("estacoes", estacaoService.listar());

        return "cptm/ajuda";
    }

    @PostMapping("/enviar")
    public String enviarSolicitacaoAjuda(@ModelAttribute SolicitacaoAjuda solicitacaoAjuda,
                                         HttpSession session,
                                         RedirectAttributes redirectAttributes) {
        Passageiro passageiro = (Passageiro) session.getAttribute("passageiroLogado");

        if (passageiro == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Faça login para acionar ajuda.");
            return "redirect:/cptm+/login";
        }

        solicitacaoAjuda.setPassageiro(passageiro);
        solicitacaoAjudaService.salvarOuEditar(solicitacaoAjuda);

        redirectAttributes.addFlashAttribute("mensagem", "Solicitação enviada com sucesso!");
        return "redirect:/cptm+/ajuda";
    }
}