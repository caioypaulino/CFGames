package com.project.cfgames.controllers.admin;

import com.project.cfgames.dtos.requests.GraficoRequest;
import com.project.cfgames.services.admin.AdminGraficoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;


@RestController
@RequestMapping("/admin")
public class AdminGraficoController {
    @Autowired
    AdminGraficoService adminGraficoService;

    @PostMapping("/grafico/produto") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> graficoVendaProdutos(@RequestBody @Valid GraficoRequest request) {
        return ResponseEntity.ok(adminGraficoService.createGraficoResponse(request.getDataInicio(), request.getDataFim(), true));
    }

    @PostMapping("/grafico/categoria") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> graficoVendaCategorias(@RequestBody @Valid GraficoRequest request) {
        return ResponseEntity.ok(adminGraficoService.createGraficoResponse(request.getDataInicio(), request.getDataFim(), false));
    }
}