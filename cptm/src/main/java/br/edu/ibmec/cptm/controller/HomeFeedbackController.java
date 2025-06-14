package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Feedback;
import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.model.Usuario;
import br.edu.ibmec.cptm.service.FeedbackService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/cptm+/feedback")
public class HomeFeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public String exibirFormularioFeedback(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("passageiroLogado");
        model.addAttribute("logado", usuario != null);
        model.addAttribute("feedback", new Feedback());
        return "cptm/feedback";
    }

    @PostMapping("/enviar")
    public String enviarFeedback(@ModelAttribute Feedback feedback,
                                 HttpSession session,
                                 Model model) {
        Usuario usuario = (Usuario) session.getAttribute("passageiroLogado");

        if (usuario instanceof Passageiro passageiro) {
            feedback.setPassageiro(passageiro);
            feedback.setDataEnvio(LocalDateTime.now());

            System.out.println("Salvando feedback: ");
            System.out.println("Tipo: " + feedback.getTipo());
            System.out.println("Nota: " + feedback.getNota());
            System.out.println("Comentário: " + feedback.getComentario());
            System.out.println("Passageiro: " + passageiro.getNome());

            feedbackService.salvarOuEditar(feedback);
            model.addAttribute("mensagem", "Feedback enviado com sucesso!");
            model.addAttribute("logado", true);
        } else {
            return "redirect:/cptm+/login";
        }

        return "redirect:/cptm+/home?from=feedback";
    }

}
