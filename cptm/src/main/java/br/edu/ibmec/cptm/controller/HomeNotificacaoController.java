package br.edu.ibmec.cptm.controller;


import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.model.Notificacao;
import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.model.Usuario;
import br.edu.ibmec.cptm.service.NotificacaoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cptm+/home")
public class HomeNotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private br.edu.ibmec.cptm.service.PassageiroService passageiroService;


    @GetMapping({"", "/"})
    public String notificacoesHome(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("passageiroLogado");

        List<Notificacao> notificacoes;

        if (usuario instanceof Passageiro passageiroSession) {
            Passageiro passageiro = passageiroService.buscarPorId(passageiroSession.getId());

            if (passageiro.getLinhasFavoritas() != null && !passageiro.getLinhasFavoritas().isEmpty()) {
                List<UUID> idsLinhas = passageiro.getLinhasFavoritas().stream()
                        .map(Linha::getId)
                        .toList();
                notificacoes = notificacaoService.buscarLinhas(idsLinhas);
            } else {
                notificacoes = notificacaoService.listar();
            }
        } else {
            notificacoes = notificacaoService.listar();
        }

        model.addAttribute("notificacoes", notificacoes);
        model.addAttribute("logado", usuario != null);

        return "cptm/home";
    }

}
