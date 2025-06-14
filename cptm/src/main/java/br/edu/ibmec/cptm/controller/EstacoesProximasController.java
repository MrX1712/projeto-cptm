package br.edu.ibmec.cptm.controller;

import br.edu.ibmec.cptm.model.Estacao;
import br.edu.ibmec.cptm.service.EstacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cptm+/estacoes")
public class EstacoesProximasController {

    @Autowired
    private EstacaoService estacaoService;

    @GetMapping("/mais-proxima")
    public ResponseEntity<Estacao> buscarMaisProxima(
            @RequestParam("linha") UUID linhaId,
            @RequestParam double lat,
            @RequestParam double lng) {

        List<Estacao> estacoes = estacaoService.listarPorLinha(linhaId);

        if (estacoes == null || estacoes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Estacao maisProxima = estacoes.stream()
                .min(Comparator.comparingDouble(est ->
                        calcularDistancia(lat, lng, est.getLatitude(), est.getLongitude())))
                .orElse(null);

        return ResponseEntity.ok(maisProxima);
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Raio da Terra em km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}