package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Feedback;
import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.service.FeedbackService;
import br.edu.ibmec.cptm.service.PassageiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/cptm+/adm/painel-administrativo/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private PassageiroService passageiroService;

    @GetMapping("/listar")
    public String listarFeedbacks(Model model) {
        model.addAttribute("feedbacks", feedbackService.listar());
        return "/feedback/listar"; // Certifique-se de que está em /templates/feedbacks/listar.html
    }

    @GetMapping("/novo")
    public String novoFeedback(Model model) {
        model.addAttribute("feedback", new Feedback());
        model.addAttribute("passageiros", passageiroService.listar());
        return "/feedback/inserir"; // Formulário de criação
    }

    @PostMapping("/salvar")
    public String salvarFeedback(@ModelAttribute Feedback feedback) {
        feedback.setDataEnvio(LocalDateTime.now()); // Define data atual como data de envio
        feedback.setVisto(false); // Define como "não visto"
        feedbackService.salvarOuEditar(feedback);
        return "redirect:/cptm+/adm/painel-administrativo/feedback/listar";
    }

    @PostMapping("/visto/{id}")
    public String marcarComoVisto(@PathVariable UUID id) {
        feedbackService.marcarComoVisto(id);
        return "redirect:/cptm+/adm/painel-administrativo/feedback/listar";
    }

    @PostMapping("/remover/{id}")
    public String removerFeedback(@PathVariable UUID id) {
        feedbackService.remover(id);
        return "redirect:/cptm+/adm/painel-administrativo/feedback/listar";
    }
}
