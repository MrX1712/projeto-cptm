package br.edu.ibmec.cptm.controller;


import br.edu.ibmec.cptm.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cptm+/home")
public class NotificacaoHomeController {

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping({"", "/"})
    public String notificacoesHome(Model model) {
        model.addAttribute("notificacoes", notificacaoService.listar());
        return "cptm/home";
    }

}
