package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Estacao;
import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.repository.EstacaoRepository;
import br.edu.ibmec.cptm.repository.LinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Controller
@RequestMapping("/cptm+/adm/painel-administrativo/estacoes")
public class EstacaoController {

    @Autowired
    private EstacaoRepository estacaoRepository;

    @Autowired
    private LinhaRepository linhaRepository;

    // Lista as estações
    @GetMapping("/listar")
    public String listarEstacoes(Model model) {
        model.addAttribute("estacoes", estacaoRepository.findAll());  // Lista todas as estações
        return "estacoes/listar";  // Template Thymeleaf
    }

    // Formulário para criar ou editar estação
    @GetMapping("/novo")
    public String novaEstacao(Model model) {
        model.addAttribute("estacao", new Estacao()); // Cria um novo objeto Estacao
        model.addAttribute("linhas", linhaRepository.findAll()); // Lista todas as linhas
        return "estacoes/inserir";  // Formulário para criação/edição
    }

    // Editar estação
    @GetMapping("/editar/{id}")
    public String editarEstacao(@PathVariable UUID id, Model model) {
        Estacao estacao = estacaoRepository.findById(id).orElse(null); // Obtém a estação, ou null se não existir
        if (estacao == null) {
            // Se a estação não for encontrada, redireciona para uma página de erro ou listagem
            return "redirect:/cptm+/adm/painel-administrativo/estacoes/listar";
        }
        model.addAttribute("estacao", estacao);
        model.addAttribute("linhas", linhaRepository.findAll()); // Lista todas as linhas
        return "estacoes/inserir";  // Redireciona para o formulário de edição
    }

    // Salvar estação (criar ou editar)
    @PostMapping("/salvar")
    public String salvarEstacao(@ModelAttribute Estacao estacao, @RequestParam("linhaId") UUID linhaId) {
        // Buscando a linha associada pelo ID
        Linha linha = linhaRepository.findById(linhaId)
                .orElseThrow(() -> new IllegalArgumentException("Linha inválida"));

        // Associando a linha à estação antes de salvar
        estacao.setLinha(linha);

        // Salvando a estação (criação ou edição)
        estacaoRepository.save(estacao);

        // Redireciona para a lista de estações
        return "redirect:/cptm+/adm/painel-administrativo/estacoes/listar";
    }

    // Excluir estação
    @GetMapping("/excluir/{id}")
    public String excluirEstacao(@PathVariable UUID id) {
        estacaoRepository.deleteById(id);  // Exclui a estação
        return "redirect:/cptm+/adm/painel-administrativo/estacoes/listar";  // Redireciona para a lista de estações
    }

    // Redireciona para listar estações por linha (caso exista)
    @GetMapping("/estacoes/{id}")
    public String listarEstacoesPorLinha(@PathVariable UUID id) {
        return "redirect:/cptm+/adm/painel-administrativo/estacoes?linhaId=" + id.toString();
    }
}
