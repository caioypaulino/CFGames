package com.project.cfgames.controllers.admin;

import com.project.cfgames.dtos.requests.StatusPedidoRequest;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.enums.StatusPedido;
import com.project.cfgames.repositories.CartaoPedidoRepository;
import com.project.cfgames.repositories.PedidoRepository;
import com.project.cfgames.services.DataService;
import com.project.cfgames.services.admin.AdminPedidoService;
import com.project.cfgames.validations.ValidationStatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminPedidoController {
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    CartaoPedidoRepository cartaoPedidoRepository;
    @Autowired
    ValidationStatusPedido validationStatusPedido;
    @Autowired
    DataService dataService;
    @Autowired
    AdminPedidoService adminPedidoService;

    // pedidos - readAll
    @GetMapping("/pedidos") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readAllPedido() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        return ResponseEntity.ok().body(adminPedidoService.mapPedidosResponse(pedidos));
    }

    // pedidos - readById
    @GetMapping("/pedidos/buscar/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readByIdPedido(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);

        if (pedido.isPresent()) {
            return ResponseEntity.ok().body(adminPedidoService.mapPedidosResponse(Collections.singletonList(pedido.get())));
        }
        else {
            return ResponseEntity.badRequest().body("Pedido não encontrado pelo id: " + id);
        }
    }

    // pedidos - listar pedidos aprovados
    @GetMapping("/pedidos/buscar/aprovados") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readPedidosAprovados() {
        List<Pedido> pedidos = pedidoRepository.findByStatus(StatusPedido.PAGAMENTO_APROVADO);

        return ResponseEntity.ok().body(adminPedidoService.mapPedidosResponse(pedidos));
    }

    // pedidos - listar pedidos em trânsito
    @GetMapping("/pedidos/buscar/emtransito")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readPedidosEmTransito() {
        List<Pedido> pedidos = pedidoRepository.findByStatus(StatusPedido.EM_TRANSITO);

        return ResponseEntity.ok().body(adminPedidoService.mapPedidosResponse(pedidos));
    }

    // pedidos - listar pedidos entregues
    @GetMapping("/pedidos/buscar/entregue")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readPedidosEntregues() {
        List<Pedido> pedidos = pedidoRepository.findByStatus(StatusPedido.ENTREGUE);

        return ResponseEntity.ok().body(adminPedidoService.mapPedidosResponse(pedidos));
    }

    // pedidos - atualizar status pedido para EM_TRANSITO
    @PutMapping("/pedidos/despachar/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> despacharPedido(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoRepository.findById(id).orElseThrow(EntityNotFoundException::new);

            StatusPedido statusUpdate = StatusPedido.EM_TRANSITO;

            validationStatusPedido.updateValidate(pedido.getStatus(), statusUpdate);

            pedido.setStatus(statusUpdate);
            pedido.setDataAtualizacao(dataService.getDateTimeNow());

            pedidoRepository.save(pedido);

            return ResponseEntity.ok("Pedido id:" + id + ", despachado para entrega!");

        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Pedido não encontrado pelo id: " + id);
        }
    }


    // pedidos - atualizar status pedido para ENTREGUE
    @PutMapping("/pedidos/confirmarentrega/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> confirmarEntregaPedido(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoRepository.findById(id).orElseThrow(EntityNotFoundException::new);

            StatusPedido statusUpdate = StatusPedido.ENTREGUE;

            validationStatusPedido.updateValidate(pedido.getStatus(), statusUpdate);

            pedido.setStatus(statusUpdate);
            pedido.setDataAtualizacao(dataService.getDateTimeNow());

            pedidoRepository.save(pedido);

            return ResponseEntity.ok("Pedido id:" + id + ", entregue!");
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Pedido não encontrado pelo id: " + id);
        }
    }

    // pedidos - update status
    @PutMapping("/pedidos/update/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> updateStatusPedido(@PathVariable Long id, @RequestBody StatusPedidoRequest request) {
        try {
            Pedido pedido = pedidoRepository.getReferenceById(id);

            validationStatusPedido.updateValidate(pedido.getStatus(), request.getStatusPedido());

            pedido.setStatus(request.getStatusPedido());
            pedido.setDataAtualizacao(dataService.getDateTimeNow());

            pedidoRepository.save(pedido);

            return ResponseEntity.ok("Status Pedido id:" + id + ", alterado com sucesso!");
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Pedido não encontrado pelo id: " + id);
        }
    }

    // pedidos - delete
    @DeleteMapping("/pedidos/delete/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> deletePedido(@PathVariable Long id) {
        try {
            cartaoPedidoRepository.deleteCartoes(id);
            pedidoRepository.deleteById(id);

            return ResponseEntity.ok().body("Pedido deletado com sucesso!");
        }
        catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.badRequest().body("Pedido não encontrado pelo id: " + id);
        }
    }

    // handler Plataforma Enum type Json exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String handleCustomValidationsExceptions (){
        return "Status inválido(Ex:\n0- EM_PROCESSAMENTO,\n1- PAGAMENTO_APROVADO,\n2- PAGAMENTO_REPROVADO,\n3- EM_TRANSITO,\n4- ENTREGUE," +
                "\n5- TROCA_SOLICITADA,\n6- TROCA_APROVADA,\n7- TROCA_REPROVADA,\n8- PRODUTO_TROCA_RECEBIDO,\n9- TROCA_FINALIZADA,\n10-CANCELADO).";
    }
}