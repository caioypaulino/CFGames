package com.project.cfgames.controllers.admin;

import com.project.cfgames.dtos.requests.CupomRequest;
import com.project.cfgames.entities.Categoria;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.Cupom;
import com.project.cfgames.repositories.ClienteRepository;
import com.project.cfgames.repositories.CupomRepository;
import com.project.cfgames.services.CupomService;
import com.project.cfgames.services.admin.AdminCupomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminCupomController {
    @Autowired
    CupomRepository cupomRepository;
    @Autowired
    CupomService cupomService;
    @Autowired
    AdminCupomService adminCupomService;
    @Autowired
    ClienteRepository clienteRepository;

    // cupons - create
    @PostMapping("/cupons/add")
    @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> addCupom(@RequestBody @Valid CupomRequest cupomRequest) {
        try {
            Cliente cliente = clienteRepository.getReferenceById(cupomRequest.getClienteId());

            cupomService.gerarCupom(cliente, cupomRequest.getValorDesconto());

            return ResponseEntity.ok().body("Cupom gerado com sucesso!");

        }
        catch (DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body("Cliente ID: #" + cupomRequest.getClienteId() + " não encontrado.");
        }
    }

    // cupons - readAll
    @GetMapping("/cupons")
    @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readAllCupons() {
        List<Cupom> cupons = cupomRepository.findAll();

        return ResponseEntity.ok().body(adminCupomService.mapPedidosResponse(cupons));
    }
}
