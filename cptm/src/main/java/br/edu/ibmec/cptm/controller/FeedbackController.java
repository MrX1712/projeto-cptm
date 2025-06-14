package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Feedback;
import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.service.FeedbackService;
import br.edu.ibmec.cptm.service.PassageiroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        return "feedback/listar"; // Certifique-se de que está em /templates/feedbacks/listar.html
    }

    @PostMapping("/visto/{id}")
    public String marcarComoVisto(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            feedbackService.marcarComoVisto(id);
            redirectAttributes.addFlashAttribute("mensagem", "Feedback marcado como visto com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao marcar feedback como visto: " + e.getMessage());
        }
        return "redirect:/cptm+/adm/painel-administrativo/feedback/listar";
    }

    // === PÁGINA DE CONFIRMAÇÃO DE REMOÇÃO ===
    @GetMapping("/remover/{id}")
    public String confirmarRemocaoFeedback(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        Feedback feedback = feedbackService.buscarPorId(id);
        if (feedback == null) {
            redirectAttributes.addFlashAttribute("erro", "Feedback não encontrado.");
            return "redirect:/cptm+/adm/painel-administrativo/feedback/listar";
        }
        model.addAttribute("feedback", feedback);
        return "feedback/remover";
    }

    // === DELETAR FEEDBACK (CONFIRMADO) ===
    @PostMapping("/deletar")
    public String deletarFeedback(@RequestParam("id") UUID id, RedirectAttributes redirectAttributes) {
        try {
            Feedback feedback = feedbackService.buscarPorId(id);
            if (feedback != null) {
                feedbackService.remover(id);
                redirectAttributes.addFlashAttribute("mensagem", "Feedback de '" + feedback.getPassageiro().getNome() + "' excluído com sucesso.");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Feedback não encontrado.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir feedback: " + e.getMessage());
        }
        return "redirect:/cptm+/adm/painel-administrativo/feedback/listar";
    }

    @Value("${openai.key}")
    private String openaiKey;

    @GetMapping("/relatorio-inteligente")
    public String gerarRelatorioIA(Model model) {
        List<Feedback> feedbacks = feedbackService.listar();

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules(); // Suporte a LocalDateTime

            String json = mapper.writeValueAsString(feedbacks);

            String prompt = """
            Analise os feedbacks em JSON e gere um relatório HTML limpo e estruturado seguindo estas diretrizes:

            ESTRUTURA OBRIGATÓRIA:
            <h3>📊 Resumo Geral</h3>
            <p><strong>Total de feedbacks:</strong> [número]</p>
            
            <h3>⭐ Notas Médias por Setor</h3>
            <ul>
            <li><strong>Atendimento:</strong> [nota]</li>
            <li><strong>Estrutura:</strong> [nota]</li>
            <li><strong>Segurança:</strong> [nota]</li>
            <li><strong>Aplicativo:</strong> [nota]</li>
            </ul>
            
            <h3>💡 Principais Comentários</h3>
            <ul>
            <li>[resumo do comentário mais relevante]</li>
            <li>[outro comentário importante]</li>
            </ul>
            
            <h3>🎯 Recomendações</h3>
            <ul>
            <li>[sugestão específica de melhoria]</li>
            <li>[outra recomendação prática]</li>
            </ul>

            REGRAS:
            - Use apenas as tags HTML mostradas acima
            - NÃO use h1, h2, ou outras tags não especificadas
            - Mantenha os textos concisos e diretos
            - Use emojis apenas nos títulos principais
            - Calcule médias reais dos dados fornecidos
            - NÃO inclua código HTML extra ou formatação complexa

            Dados dos feedbacks:
            """ + json;

            String requestBody = """
            {
              "model": "gpt-4o",
              "messages": [
                {"role": "user", "content": %s}
              ]
            }
            """.formatted(mapper.writeValueAsString(prompt));

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Authorization", "Bearer " + openaiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String respostaIA = response.body();

            String conteudo = mapper
                    .readTree(respostaIA)
                    .get("choices").get(0).get("message").get("content").asText()
                    .replace("```html", "")
                    .replace("```", "")
                    .trim();


            model.addAttribute("relatorioIA", conteudo);

        } catch (Exception e) {
            model.addAttribute("relatorioIA",
                    "<h3>❌ Erro na Análise</h3><p>Não foi possível gerar o relatório: " + e.getMessage() + "</p>");
        }

        model.addAttribute("feedbacks", feedbackService.listar());
        return "feedback/listar";
    }
}