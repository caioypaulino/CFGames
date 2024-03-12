package com.project.cfgames.controllers.admin;

import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.responses.CarrinhoCompraResponse;
import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.repositories.CarrinhoCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminCarrinhoCompraController {
    @Autowired
    CarrinhoCompraRepository carrinhoCompraRepository;

    // carrinhos de compra - readAll
    @GetMapping("/carrinhosdecompra") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readAllCarrinhos() {
        List<CarrinhoCompra> carrinhos = carrinhoCompraRepository.findAll();

        List<CarrinhoCompraResponse> carrinhosCompraResponse = new ArrayList<>();

        for (CarrinhoCompra carrinho : carrinhos) {
            CarrinhoCompraResponse carrinhoCompraResponse = new CarrinhoCompraResponse();

            CustomMapper.update(carrinho, carrinhoCompraResponse);

            carrinhosCompraResponse.add(carrinhoCompraResponse);
        }

        return ResponseEntity.ok().body(carrinhosCompraResponse);
    }

    // carrinhos de compra - readById
    @GetMapping("/carrinhosdecompra/buscar/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readByIdCarrinhos(@PathVariable Long id) {
        Optional<CarrinhoCompra> carrinho = carrinhoCompraRepository.findById(id);

        if (carrinho.isPresent()) {
            CarrinhoCompraResponse carrinhoCompraResponse = new CarrinhoCompraResponse();

            CustomMapper.update(carrinho.get(), carrinhoCompraResponse);

            return ResponseEntity.ok().body(carrinhoCompraResponse);
        }
        else {
            return ResponseEntity.badRequest().body("Carrinho de Compra não encontrado pelo id: " + id);
        }
    }
}
