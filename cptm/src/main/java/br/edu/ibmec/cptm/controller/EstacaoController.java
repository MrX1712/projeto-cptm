package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Estacao;
import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.service.EstacaoService;
import br.edu.ibmec.cptm.service.LinhaService;
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
    private EstacaoService estacaoService;

    @Autowired
    private LinhaService linhaService;

    @GetMapping("/listar")
    public String listarEstacoes(@RequestParam(required = false) UUID linhaId, Model model) {
        List<Estacao> estacoes;
        if (linhaId != null) {
            estacoes = estacaoService.listarPorLinha(linhaId);
            model.addAttribute("linha", linhaService.buscarPorId(linhaId));
        } else {
            estacoes = estacaoService.listar();
        }
        model.addAttribute("estacoes", estacoes);
        return "estacoes/listar";
    }

    @GetMapping("/json/{linhaId}")
    @ResponseBody
    public List<Estacao> listarEstacoesPorLinhaJson(@PathVariable UUID linhaId) {
        List<Estacao> estacoes = estacaoService.listarPorLinha(linhaId);
        System.out.println("Retornando " + estacoes.size() + " estações da linha " + linhaId);
        return estacoes;
    }

    @GetMapping("/novo")
    public String novaEstacao(Model model) {
        model.addAttribute("estacao", new Estacao());
        model.addAttribute("linhas", linhaService.listar());
        return "estacoes/inserir";
    }

    @PostMapping("/salvar")
    public String salvarEstacao(@ModelAttribute Estacao estacao, @RequestParam("linhaId") UUID linhaId, RedirectAttributes redirectAttributes) {
        try {
            Linha linha = linhaService.buscarPorId(linhaId);
            if (linha == null) {
                throw new IllegalArgumentException("Linha inválida");
            }
            estacao.setLinha(linha);
            estacaoService.salvarOuEditar(estacao);
            redirectAttributes.addFlashAttribute("mensagem", "Estação '" + estacao.getNome() + "' salva com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar estação: " + e.getMessage());
        }
        return "redirect:/cptm+/adm/painel-administrativo/estacoes/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarEstacao(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        Estacao estacao = estacaoService.buscarPorId(id);
        if (estacao == null) {
            redirectAttributes.addFlashAttribute("erro", "Estação não encontrada.");
            return "redirect:/cptm+/adm/painel-administrativo/estacoes/listar";
        }
        model.addAttribute("estacao", estacao);
        model.addAttribute("linhas", linhaService.listar());
        return "estacoes/editar";
    }

    @PostMapping("/salvar-edicao")
    public String salvarEditarEstacao(@ModelAttribute Estacao estacao, RedirectAttributes redirectAttributes) {
        try {
            Estacao estacaoExistente = estacaoService.buscarPorId(estacao.getId());
            if (estacaoExistente == null) {
                throw new IllegalArgumentException("Estação não encontrada");
            }

            estacaoExistente.setNome(estacao.getNome());
            estacaoExistente.setNumero(estacao.getNumero());
            estacaoExistente.setLatitude(estacao.getLatitude());
            estacaoExistente.setLongitude(estacao.getLongitude());
            estacaoService.salvarOuEditar(estacaoExistente);

            redirectAttributes.addFlashAttribute("mensagem", "Estação '" + estacao.getNome() + "' editada com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao editar estação: " + e.getMessage());
        }

        return "redirect:/cptm+/adm/painel-administrativo/estacoes/listar";
    }

    @GetMapping("/remover/{id}")
    public String confirmarRemocaoEstacao(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        Estacao estacao = estacaoService.buscarPorId(id);
        if (estacao == null) {
            redirectAttributes.addFlashAttribute("erro", "Estação não encontrada.");
            return "redirect:/cptm+/adm/painel-administrativo/estacoes/listar";
        }
        model.addAttribute("estacao", estacao);
        return "estacoes/remover";
    }

    @PostMapping("/deletar")
    public String deletarEstacao(@RequestParam("id") UUID id, RedirectAttributes redirectAttributes) {
        try {
            Estacao estacao = estacaoService.buscarPorId(id);
            if (estacao != null) {
                estacaoService.remover(estacao);
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