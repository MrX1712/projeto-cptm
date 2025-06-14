package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.model.Notificacao;
import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.model.Usuario;
import br.edu.ibmec.cptm.service.LinhaService;
import br.edu.ibmec.cptm.service.NotificacaoService;
import br.edu.ibmec.cptm.service.PassageiroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cptm+")
public class CptmController {

    @Autowired
    private PassageiroService passageiroService;

    @Autowired
    private LinhaService linhaService;

    @Autowired
    private NotificacaoService notificacaoService;

    private void adicionarStatusLogado(HttpSession session, Model model) {
        Passageiro passageiro = (Passageiro) session.getAttribute("passageiroLogado");
        boolean logado = passageiro != null;
        model.addAttribute("logado", logado);
        if (logado) {
            model.addAttribute("passageiroNome", passageiro.getNome());
            model.addAttribute("passageiroId", passageiro.getId());
        }
    }

    @RequestMapping("/home")
    public String home(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("passageiroLogado");

        List<Notificacao> notificacoes;

        if (usuario instanceof Passageiro passageiro) {
            Passageiro passageiroCompleto = passageiroService.buscarComLinhasFavoritas(passageiro.getId());

            if (passageiroCompleto != null && passageiroCompleto.getLinhasFavoritas() != null && !passageiroCompleto.getLinhasFavoritas().isEmpty()) {
                List<UUID> idsLinhas = passageiroCompleto.getLinhasFavoritas().stream()
                        .map(Linha::getId)
                        .toList();
                notificacoes = notificacaoService.buscarLinhas(idsLinhas);
            } else {
                notificacoes = notificacaoService.listar();
            }

            model.addAttribute("logado", true);
            model.addAttribute("passageiroNome", passageiro.getNome());
            model.addAttribute("passageiroId", passageiro.getId());
        } else {
            notificacoes = notificacaoService.listar();
            model.addAttribute("logado", false);
        }

        model.addAttribute("notificacoes", notificacoes);
        return "cptm/home";
    }

    @RequestMapping("/login")
    public String login(HttpSession session, Model model) {
        adicionarStatusLogado(session, model);
        return "cptm/login";
    }

    @RequestMapping("/estacoes")
    public String estacoes(HttpSession session, Model model) {
        adicionarStatusLogado(session, model);

        List<Linha> linhas = linhaService.listar();
        model.addAttribute("linhas", linhas);

        return "cptm/estacoes";
    }

    @RequestMapping("/feedback")
    public String feedback(HttpSession session, Model model) {
        adicionarStatusLogado(session, model);
        return "cptm/feedback";
    }

    @RequestMapping("/cadastro")
    public String cadastro(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Passageiro passageiro = (Passageiro) session.getAttribute("passageiroLogado");

        if (passageiro != null) {
            redirectAttributes.addFlashAttribute("mensagem", "Você já está logado!");
            return "redirect:/cptm+/home?from=cadastro";
        }

        model.addAttribute("passageiro", new Passageiro());
        adicionarStatusLogado(session, model);
        return "cptm/cadastro";
    }

    @RequestMapping("/rastreamento")
    public String rastreamento(HttpSession session, Model model) {
        adicionarStatusLogado(session, model);
        return "cptm/rastreamento";
    }

    @GetMapping("/usuario")
    public String exibirPerfil(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("passageiroLogado");

        if (usuario instanceof Passageiro passageiroOriginal) {
            Passageiro passageiro = passageiroService.buscarPorId(passageiroOriginal.getId());
            passageiro.getLinhasFavoritas().size();

            List<Linha> todasAsLinhas = linhaService.listar();

            model.addAttribute("passageiro", passageiro);
            model.addAttribute("todasAsLinhas", todasAsLinhas);
            model.addAttribute("logado", true);
            model.addAttribute("passageiroNome", passageiro.getNome());
            model.addAttribute("passageiroId", passageiro.getId());

            return "cptm/usuario";
        }

        return "redirect:/cptm+/login";
    }

    @PostMapping("/usuario/atualizar")
    public String atualizarPerfil(@ModelAttribute("passageiro") Passageiro passageiroForm,
                                  @RequestParam(name = "linhasFavoritas", required = false) List<UUID> idsDasLinhas,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        Passageiro passageiro = passageiroService.buscarPorId(passageiroForm.getId());

        List<Linha> favoritas = new ArrayList<>();
        if (idsDasLinhas != null) {
            for (UUID id : idsDasLinhas) {
                Linha linha = linhaService.buscarPorId(id);
                if (linha != null) favoritas.add(linha);
            }
        }

        passageiro.setNome(passageiroForm.getNome());
        passageiro.setEmail(passageiroForm.getEmail());
        passageiro.setSenha(passageiroForm.getSenha());
        passageiro.setLinhasFavoritas(favoritas);

        passageiroService.salvarOuEditar(passageiro);
        session.setAttribute("passageiroLogado", passageiro);

        redirectAttributes.addFlashAttribute("mensagem", "Dados atualizados com sucesso!");
        return "redirect:/cptm+/usuario";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/cptm+/home";
    }
}