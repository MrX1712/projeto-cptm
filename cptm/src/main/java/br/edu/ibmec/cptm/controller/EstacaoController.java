package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Estacao;
import br.edu.ibmec.cptm.model.Linha;
import br.edu.ibmec.cptm.repository.EstacaoRepository;
import br.edu.ibmec.cptm.repository.LinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cptm+/adm/painel-administrativo/estacoes")
public class EstacaoController {

    @Autowired
    private EstacaoRepository estacaoRepository;

    @Autowired
    private LinhaRepository linhaRepository;

    // Lista as estações
// Lista as estações (filtradas por linha, se a linhaId for fornecida)
    @GetMapping("/listar")
    public String listarEstacoes(@RequestParam(required = false) UUID linhaId, Model model) {
        List<Estacao> estacoes;

        // Se linhaId for fornecido, filtra as estações pela linha
        if (linhaId != null) {
            Linha linha = linhaRepository.findById(linhaId).orElse(null); // Recupera a linha
            if (linha != null) {
                estacoes = estacaoRepository.findAllByLinha(linha); // Filtra as estações pela linha
            } else {
                estacoes = new ArrayList<>();  // Se a linha não for encontrada, exibe uma lista vazia
            }
            model.addAttribute("linha", linha);  // Adiciona a linha ao modelo
        } else {
            estacoes = estacaoRepository.findAll();  // Caso não tenha linhaId, lista todas as estações
        }

        model.addAttribute("estacoes", estacoes);  // Passa as estações para a view
        return "estacoes/listar";  // Retorna para a página de listagem de estações
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
        return "estacoes/editar";  // Redireciona para o formulário de edição
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

    // Salvar estação (editar apenas o nome)
    @PostMapping("/salvar-edicao")
    public String salvarEditarEstacao(@ModelAttribute Estacao estacao) {
        // Verificando se a estação existe no banco
        Estacao estacaoExistente = estacaoRepository.findById(estacao.getId())
                .orElseThrow(() -> new IllegalArgumentException("Estação não encontrada"));

        // Atualizando apenas o nome da estação (preservando a linha)
        estacaoExistente.setNome(estacao.getNome());

        // Salvando a estação atualizada
        estacaoRepository.save(estacaoExistente);

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
