package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Estacao;
import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.repository.EstacaoRepository;
import br.edu.ibmec.cptm.repository.LinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cptm+/adm/painel-administrativo/estacoes")
public class EstacaoController {

    @Autowired
    private EstacaoRepository estacaoRepository;

    @Autowired
    private LinhaRepository linhaRepository;

    @GetMapping("/listar")
    public String listarEstacoes(@RequestParam(required = false) UUID linhaId, Model model) {
        List<Estacao> estacoes;
        if (linhaId != null) {
            estacoes = estacaoRepository.findAllByLinhaId(linhaId);
            model.addAttribute("linha", linhaRepository.findById(linhaId).orElse(null));
        } else {
            estacoes = estacaoRepository.findAll();
        }
        model.addAttribute("estacoes", estacoes);
        return "estacoes/listar";
    }

    @GetMapping("/json/{linhaId}")
    @ResponseBody
    public List<Estacao> listarEstacoesPorLinhaJson(@PathVariable UUID linhaId) {
        List<Estacao> estacoes = estacaoRepository.findAllByLinhaId(linhaId);
        System.out.println("Retornando " + estacoes.size() + " estações da linha " + linhaId);
        return estacoes;
    }

    @GetMapping("/novo")
    public String novaEstacao(Model model) {
        model.addAttribute("estacao", new Estacao());
        model.addAttribute("linhas", linhaRepository.findAll());
        return "estacoes/inserir";
    }

    @PostMapping("/salvar")
    public String salvarEstacao(@ModelAttribute Estacao estacao, @RequestParam("linhaId") UUID linhaId, RedirectAttributes redirectAttributes) {
        try {
            Linha linha = linhaRepository.findById(linhaId)
                    .orElseThrow(() -> new IllegalArgumentException("Linha inválida"));
            estacao.setLinha(linha);
            estacaoRepository.save(estacao);
            redirectAttributes.addFlashAttribute("mensagem", "Estação '" + estacao.getNome() + "' salva com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar estação: " + e.getMessage());
        }
        return "redirect:/cptm+/adm/painel-administrativo/estacoes/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarEstacao(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        Estacao estacao = estacaoRepository.findById(id).orElse(null);
        if (estacao == null) {
            redirectAttributes.addFlashAttribute("erro", "Estação não encontrada.");
            return "redirect:/cptm+/adm/painel-administrativo/estacoes/listar";
        }
        model.addAttribute("estacao", estacao);
        model.addAttribute("linhas", linhaRepository.findAll());
        return "estacoes/editar";
    }

    @PostMapping("/salvar-edicao")
    public String salvarEditarEstacao(@ModelAttribute Estacao estacao, RedirectAttributes redirectAttributes) {
        try {
            Estacao estacaoExistente = estacaoRepository.findById(estacao.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Estação não encontrada"));

            estacaoExistente.setNome(estacao.getNome());
            estacaoExistente.setNumero(estacao.getNumero());
            estacaoExistente.setLatitude(estacao.getLatitude());
            estacaoExistente.setLongitude(estacao.getLongitude());
            estacaoRepository.save(estacaoExistente);

            redirectAttributes.addFlashAttribute("mensagem", "Estação '" + estacao.getNome() + "' editada com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao editar estação: " + e.getMessage());
        }

        return "redirect:/cptm+/adm/painel-administrativo/estacoes/listar";
    }

    // === PÁGINA DE CONFIRMAÇÃO DE REMOÇÃO ===
    @GetMapping("/remover/{id}")
    public String confirmarRemocaoEstacao(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        Estacao estacao = estacaoRepository.findById(id).orElse(null);
        if (estacao == null) {
            redirectAttributes.addFlashAttribute("erro", "Estação não encontrada.");
            return "redirect:/cptm+/adm/painel-administrativo/estacoes/listar";
        }
        model.addAttribute("estacao", estacao);
        return "estacoes/remover";
    }

    // === DELETAR ESTAÇÃO (CONFIRMADO) ===
    @PostMapping("/deletar")
    public String deletarEstacao(@RequestParam("id") UUID id, RedirectAttributes redirectAttributes) {
        try {
            Estacao estacao = estacaoRepository.findById(id).orElse(null);
            if (estacao != null) {
                estacaoRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("mensagem", "Estação '" + estacao.getNome() + "' excluída com sucesso.");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Estação não encontrada.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir estação: " + e.getMessage());
        }
        return "redirect:/cptm+/adm/painel-administrativo/estacoes/listar";
    }
}