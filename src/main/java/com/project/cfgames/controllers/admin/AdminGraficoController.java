package com.project.cfgames.controllers.admin;

import com.project.cfgames.dtos.requests.GraficoRequest;
import com.project.cfgames.dtos.responses.GraficoResponse;
import com.project.cfgames.repositories.CategoriaRepository;
import com.project.cfgames.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminGraficoController {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;

    @GetMapping("/grafico") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> graficoVenda(@RequestBody @Valid GraficoRequest request) {
        List<GraficoResponse> graficoResponses = new ArrayList<>();
        List<Object[]> stats;

        if (request.isProduto()) {
            stats = produtoRepository.selectStatsProdutos(request.getDataInicio(), request.getDataFim());
        }
        else {
            stats = categoriaRepository.selectStatsCategorias(request.getDataInicio(), request.getDataFim());
        }

        // Converte cada resultado da query em DTO e adiciona ao array
        for (Object[] stat : stats) {
            String nome = (String) stat[0];
            Integer quantidadeVendida = ((BigInteger) stat[1]).intValue();
            Float valorTotal = (Float) stat[2];

            GraficoResponse graficoResponse = new GraficoResponse(nome, quantidadeVendida, valorTotal);

            graficoResponses.add(graficoResponse);
        }

        return ResponseEntity.ok(graficoResponses);
    }
}