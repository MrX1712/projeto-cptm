package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Feedback;
import br.edu.ibmec.cptm.model.Passageiro;
import br.edu.ibmec.cptm.service.FeedbackService;
import br.edu.ibmec.cptm.service.PassageiroService;
import com.fasterxml.jackson.databind.JsonNode;
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

    @GetMapping("/listar")
    public String listarFeedbacks(Model model) {
        model.addAttribute("feedbacks", feedbackService.listar());
        return "feedback/listar";
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
            mapper.findAndRegisterModules();

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

            // Primeiro, teste com modelo mais comum
            String requestBody = """
        {
          "model": "gpt-3.5-turbo",
          "messages": [
            {"role": "user", "content": %s}
          ],
          "max_tokens": 1000,
          "temperature": 0.7
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

            // Debug: Log da resposta completa
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            // Verificar se a requisição foi bem-sucedida
            if (response.statusCode() != 200) {
                throw new RuntimeException("API retornou status " + response.statusCode() + ": " + response.body());
            }

            String respostaIA = response.body();

            // Parse seguro da resposta JSON
            JsonNode rootNode = mapper.readTree(respostaIA);

            // Verificar se existe o campo "choices"
            if (!rootNode.has("choices")) {
                throw new RuntimeException("Resposta da API não contém o campo 'choices'. Resposta: " + respostaIA);
            }

            JsonNode choicesNode = rootNode.get("choices");

            // Verificar se choices é um array e não está vazio
            if (!choicesNode.isArray() || choicesNode.size() == 0) {
                throw new RuntimeException("Campo 'choices' está vazio ou não é um array. Resposta: " + respostaIA);
            }

            JsonNode firstChoice = choicesNode.get(0);

            // Verificar se existe message
            if (!firstChoice.has("message")) {
                throw new RuntimeException("Primeira escolha não contém 'message'. Resposta: " + respostaIA);
            }

            JsonNode messageNode = firstChoice.get("message");

            // Verificar se existe content
            if (!messageNode.has("content")) {
                throw new RuntimeException("Message não contém 'content'. Resposta: " + respostaIA);
            }

            String conteudo = messageNode.get("content").asText()
                    .replace("```html", "")
                    .replace("```", "")
                    .trim();

            model.addAttribute("relatorioIA", conteudo);

        } catch (Exception e) {
            // Log do erro completo para debug
            e.printStackTrace();

            model.addAttribute("relatorioIA",
                    "<h3>❌ Erro na Análise</h3>" +
                            "<p>Não foi possível gerar o relatório.</p>" +
                            "<p><strong>Detalhes:</strong> " + e.getMessage() + "</p>");
        }

        model.addAttribute("feedbacks", feedbackService.listar());
        return "feedback/listar";
    }
}