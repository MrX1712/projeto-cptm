package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Usuario;
import br.edu.ibmec.cptm.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/cptm+")
public class RecuperarSenhaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/verificar-dados-recuperacao")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> verificarDadosRecuperacao(
            @RequestBody Map<String, String> dados) {

        Map<String, Object> response = new HashMap<>();

        String email = dados.get("email");
        String cpf = dados.get("cpf");

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailAndCpf(email, cpf);

        if (usuarioOpt.isPresent()) {
            response.put("sucesso", true);
            response.put("usuarioId", usuarioOpt.get().getId().toString());
        } else {
            response.put("sucesso", false);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/recuperar-senha")
    public String mostrarPaginaRecuperarSenha(@RequestParam("id") String usuarioId,
                                              @RequestParam(value = "sucesso", required = false) String sucesso,
                                              Model model) {

        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            return "redirect:/cptm+/login";
        }

        try {
            UUID id = UUID.fromString(usuarioId);
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

            if (!usuarioOpt.isPresent()) {
                return "redirect:/cptm+/login";
            }

        } catch (IllegalArgumentException e) {
            return "redirect:/cptm+/login";
        }

        model.addAttribute("usuarioId", usuarioId);
        model.addAttribute("logado", false);

        if ("true".equals(sucesso)) {
            model.addAttribute("senhaAlterada", true);
        }

        return "cptm/recuperar";
    }

    @PostMapping("/recuperar-senha")
    public String processarRecuperarSenha(@RequestParam("usuarioId") String usuarioId,
                                          @RequestParam("novaSenha") String novaSenha,
                                          @RequestParam("confirmarSenha") String confirmarSenha,
                                          Model model) {

        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            model.addAttribute("erro", "Nova senha é obrigatória");
            model.addAttribute("usuarioId", usuarioId);
            model.addAttribute("logado", false);
            return "cptm/recuperar";
        }

        if (confirmarSenha == null || confirmarSenha.trim().isEmpty()) {
            model.addAttribute("erro", "Confirmação de senha é obrigatória");
            model.addAttribute("usuarioId", usuarioId);
            model.addAttribute("logado", false);
            return "cptm/recuperar";
        }

        if (!novaSenha.equals(confirmarSenha)) {
            model.addAttribute("erro", "As senhas não coincidem");
            model.addAttribute("usuarioId", usuarioId);
            model.addAttribute("logado", false);
            return "cptm/recuperar";
        }

        if (novaSenha.length() < 6) {
            model.addAttribute("erro", "A senha deve ter pelo menos 6 caracteres");
            model.addAttribute("usuarioId", usuarioId);
            model.addAttribute("logado", false);
            return "cptm/recuperar";
        }

        try {
            UUID id = UUID.fromString(usuarioId);
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();

                usuario.setSenha(novaSenha);

                usuarioRepository.save(usuario);

                return "redirect:/cptm+/recuperar-senha?id=" + usuarioId + "&sucesso=true";
            } else {
                model.addAttribute("erro", "Usuário não encontrado");
                model.addAttribute("usuarioId", usuarioId);
                model.addAttribute("logado", false);
                return "cptm/recuperar";
            }

        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", "ID de usuário inválido");
            model.addAttribute("usuarioId", usuarioId);
            model.addAttribute("logado", false);
            return "cptm/recuperar";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro interno do servidor. Tente novamente.");
            model.addAttribute("usuarioId", usuarioId);
            model.addAttribute("logado", false);
            return "cptm/recuperar";
        }
    }
}