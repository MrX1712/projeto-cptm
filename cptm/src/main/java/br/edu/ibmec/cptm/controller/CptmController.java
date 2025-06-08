package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.model.Usuario;
import br.edu.ibmec.cptm.repository.PassageiroRepository;
import br.edu.ibmec.cptm.service.PassageiroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cptm+")
public class CptmController {

    @Autowired
    private PassageiroService passageiroService;

    @RequestMapping("/home")
    public String home() {
        return "cptm/home";
    }

    @RequestMapping("/login")
    public String login() {
        return "cptm/login";
    }

    @RequestMapping("/ajuda")
    public String ajuda() {
        return "cptm/ajuda";
    }

    @RequestMapping("/estacoes")
    public String estacoes() {
        return "cptm/estacoes";
    }

    @RequestMapping("/feedback")
    public String feedback() {
        return "cptm/feedback";
    }

    @RequestMapping("/cadastro")
    public String cadastro() {
        return "cptm/cadastro";
    }

    @RequestMapping("/rastreamento")
    public String rastreamento() {
        return "cptm/rastreamento";
    }

    @RequestMapping("/usuario")
    public String login(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario instanceof Passageiro passageiro) {
            model.addAttribute("passageiro", passageiro);
            return "cptm/usuario";
        } else {
            return "redirect:/cptm+/login";
        }
    }

    @PostMapping("/usuario/atualizar")
    public String atualizarPerfil(@ModelAttribute Passageiro passageiro,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        Passageiro atualizado = passageiroService.salvarOuEditar(passageiro);

        session.setAttribute("usuario", atualizado);

        redirectAttributes.addFlashAttribute("mensagem", "Dados atualizados com sucesso!");
        return "redirect:/cptm+/usuario";
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // encerra a sessão
        return "redirect:/cptm+/home"; // redireciona para a página inicial
    }
}
