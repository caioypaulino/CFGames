package com.project.cfgames.controllers.admin;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.requests.CupomRequest;
import com.project.cfgames.dtos.requests.CupomUpdateRequest;
import com.project.cfgames.dtos.requests.ProdutoRequest;
import com.project.cfgames.entities.Categoria;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.Cupom;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.ClienteRepository;
import com.project.cfgames.repositories.CupomRepository;
import com.project.cfgames.services.CupomService;
import com.project.cfgames.services.admin.AdminCupomService;
import com.project.cfgames.validations.ValidationCupom;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
    ValidationCupom validationCupom;
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

    // cupons - update
    @PutMapping("/cupons/update/{codigoCupom}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> updateCupom(@PathVariable String codigoCupom, @RequestBody @Valid CupomUpdateRequest request) {
        try {
            Cupom cupom = cupomRepository.getReferenceById(codigoCupom);

            validationCupom.updateValidate(request, cupom);

            CustomMapper.update(request, cupom);

            cupomRepository.save(cupom);

            return ResponseEntity.ok().body("Cupom atualizado com sucesso!");
        }
        catch (EntityNotFoundException | MappingException ex) {
            return ResponseEntity.badRequest().body("Cupom não encontrado pelo código: " + codigoCupom);
        }
    }

    // cupons - desativar
    @DeleteMapping("/cupons/desativar/{codigoCupom}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> desativarCupom(@PathVariable String codigoCupom) {
        try {
            Cupom cupom = cupomRepository.getReferenceById(codigoCupom);

            validationCupom.cupomIndisponivelValidate(cupom);

            cupom.setDisponivel(false);

            cupomRepository.save(cupom);

            return ResponseEntity.ok().body("Cupom desativado com sucesso!");
        }
        catch (EntityNotFoundException | MappingException ex) {
            return ResponseEntity.badRequest().body("Cupom não encontrado pelo código: " + codigoCupom);
        }
    }

    // cupons - delete
    @DeleteMapping("/produtos/delete/{codigoCupom}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> deleteCupom(@PathVariable String codigoCupom) {
        try {
            cupomRepository.removePedido(codigoCupom);
            cupomRepository.deleteById(codigoCupom);

            return ResponseEntity.ok().body("Cupom deletado com sucesso!");
        }
        catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.badRequest().body("Cupom não encontrado pelo código: " + codigoCupom);
        }
    }
}
