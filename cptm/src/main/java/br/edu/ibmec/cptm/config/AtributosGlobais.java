package br.edu.ibmec.cptm.config;

import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AtributosGlobais {

    @ModelAttribute("logado")
    public boolean logado(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("passageiroLogado");
        return usuario instanceof Passageiro;
    }

    @ModelAttribute("passageiroLogado")
    public Passageiro passageiroLogado(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("passageiroLogado");
        return usuario instanceof Passageiro ? (Passageiro) usuario : null;
    }
}