package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.model.TimeCptm;
import br.edu.ibmec.cptm.model.Usuario;
import br.edu.ibmec.cptm.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailAndSenha(email, password);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (usuario instanceof Passageiro) {
                session.setAttribute("passageiroLogado", usuario);
                return "redirect:/cptm+/usuario";
            } else if (usuario instanceof TimeCptm) {
                session.setAttribute("timeCptmLogado", usuario);
                return "redirect:/cptm+/adm/painel-administrativo";
            }
        }

        model.addAttribute("loginInvalido", true);
        return "cptm/login";
    }

}
