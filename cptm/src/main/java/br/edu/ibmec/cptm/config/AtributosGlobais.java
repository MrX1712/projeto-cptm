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
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return usuario instanceof Passageiro;
    }
}
