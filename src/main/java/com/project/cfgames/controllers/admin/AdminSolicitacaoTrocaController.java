package com.project.cfgames.controllers.admin;

import com.project.cfgames.dtos.requests.ReposicaoEstoqueRequest;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.SolicitacaoTroca;
import com.project.cfgames.entities.enums.StatusPedido;
import com.project.cfgames.entities.enums.StatusSolicitacao;
import com.project.cfgames.repositories.ItemTrocaRepository;
import com.project.cfgames.repositories.SolicitacaoTrocaRepository;
import com.project.cfgames.services.CupomService;
import com.project.cfgames.services.EstoqueService;
import com.project.cfgames.services.SolicitacaoTrocaService;
import com.project.cfgames.services.admin.AdminSolicitacaoTrocaService;
import com.project.cfgames.validations.ValidationReposicaoEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminSolicitacaoTrocaController {
    @Autowired
    AdminSolicitacaoTrocaService adminSolicitacaoTrocaService;
    @Autowired
    SolicitacaoTrocaService solicitacaoTrocaService;
    @Autowired
    SolicitacaoTrocaRepository solicitacaoTrocaRepository;
    @Autowired
    ItemTrocaRepository itemTrocaRepository;
    @Autowired
    EstoqueService estoqueService;
    @Autowired
    ValidationReposicaoEstoque validationReposicaoEstoque;
    @Autowired
    CupomService cupomService;

    // solicitacoes troca - readAll
    @GetMapping("/solicitacoestroca") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readAllSolicitacaoTroca() {
        List<SolicitacaoTroca> solicitacoes = solicitacaoTrocaRepository.findAll();

        return ResponseEntity.ok().body(adminSolicitacaoTrocaService.mapSolicitacoesTrocaResponse(solicitacoes));
    }

    // solicitacoes troca - readById
    @GetMapping("/solicitacoestroca/buscar/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readByIdSolicitacaoTroca(@PathVariable Long id) {
        Optional<SolicitacaoTroca> solicitacao = solicitacaoTrocaRepository.findById(id);

        if (solicitacao.isPresent()) {
            return ResponseEntity.ok().body(adminSolicitacaoTrocaService.mapSolicitacoesTrocaResponse(Collections.singletonList(solicitacao.get())));
        }
        else {
            return ResponseEntity.badRequest().body("Solicitação de Troca não encontrada pelo id: " + id);
        }
    }

    // solicitacoes troca - listar trocas pendentes
    @GetMapping("/solicitacoestroca/buscar/pendentes") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readTrocasPendentes() {
        List<SolicitacaoTroca> solicitacoes = solicitacaoTrocaRepository.findByStatus(StatusSolicitacao.PENDENTE);

        return ResponseEntity.ok().body(adminSolicitacaoTrocaService.mapSolicitacoesTrocaResponse(solicitacoes));
    }

    // solicitacoes troca - listar trocas aprovadas
    @GetMapping("/solicitacoestroca/buscar/aprovadas") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readTrocasAprovadas() {
        List<SolicitacaoTroca> solicitacoes = solicitacaoTrocaRepository.findByStatus(StatusSolicitacao.APROVADA);

        return ResponseEntity.ok().body(adminSolicitacaoTrocaService.mapSolicitacoesTrocaResponse(solicitacoes));
    }

    // solicitacoes troca - listar trocas reprovadas
    @GetMapping("/solicitacoestroca/buscar/reprovadas") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readTrocasReprovadas() {
        List<SolicitacaoTroca> solicitacoes = solicitacaoTrocaRepository.findByStatus(StatusSolicitacao.REPROVADA);

        return ResponseEntity.ok().body(adminSolicitacaoTrocaService.mapSolicitacoesTrocaResponse(solicitacoes));
    }

    // solicitacoes troca - listar trocas concluídas
    @GetMapping("/solicitacoestroca/buscar/concluidas") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> readTrocasConcluidas() {
        List<SolicitacaoTroca> solicitacoes = solicitacaoTrocaRepository.findByStatus(StatusSolicitacao.CONCLUIDA);

        return ResponseEntity.ok().body(adminSolicitacaoTrocaService.mapSolicitacoesTrocaResponse(solicitacoes));
    }

    // solicitacoes troca - atualizar status solicitacao para APROVADA
    @PutMapping("/solicitacoestroca/aprovar/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> aprovarTroca(@PathVariable Long id) {
        try {
            SolicitacaoTroca solicitacaoTroca = solicitacaoTrocaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            Pedido pedido = solicitacaoTroca.getPedido();

            solicitacaoTrocaService.updateStatusTroca(solicitacaoTroca, pedido, StatusSolicitacao.REPROVADA, StatusPedido.TROCA_REPROVADA);

            return ResponseEntity.ok("Solicitação de Troca id: " + id + " do Pedido id: " + pedido.getId() + ", teve o status aprovado, aguardando recebimento dos itens!");

        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Solicitação de Troca não encontrada pelo id: " + id);
        }
    }

    // solicitacoes troca - atualizar status solicitacao para REPROVADA
    @PutMapping("/solicitacoestroca/reprovar/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> reprovarTroca(@PathVariable Long id) {
        try {
            SolicitacaoTroca solicitacaoTroca = solicitacaoTrocaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            Pedido pedido = solicitacaoTroca.getPedido();

            solicitacaoTrocaService.updateStatusTroca(solicitacaoTroca, pedido, StatusSolicitacao.REPROVADA, StatusPedido.TROCA_REPROVADA);

            return ResponseEntity.ok("Solicitação de Troca id: " + id + " do Pedido id: " + pedido.getId() + ", teve o status reprovado.");
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Solicitação de Troca não encontrada pelo id: " + id);
        }
    }

    // solicitacoes troca - concluir troca
    @PutMapping("/solicitacoestroca/concluir/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> concluirTroca(@PathVariable Long id, @RequestBody @Valid ReposicaoEstoqueRequest request) {
        try {
            SolicitacaoTroca solicitacaoTroca = solicitacaoTrocaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            Pedido pedido = solicitacaoTroca.getPedido();

            validationReposicaoEstoque.itensReposicaoValidate(solicitacaoTroca, request);

            solicitacaoTrocaService.updateStatusTroca(solicitacaoTroca, pedido, StatusSolicitacao.PRODUTOS_RECEBIDOS, StatusPedido.PRODUTOS_TROCA_RECEBIDOS);

            // lógica para re-adicionar ao estoque ou não
            if (request.getReporEstoque()) {
                estoqueService.reporEstoqueTroca(request.getItensReposicao());
            }

            // lógica para gerar cupom
            cupomService.gerarCupom(solicitacaoTroca);

            solicitacaoTrocaService.updateStatusTroca(solicitacaoTroca, pedido, StatusSolicitacao.CONCLUIDA, StatusPedido.TROCA_FINALIZADA);

            return ResponseEntity.ok("Solicitação de Troca id: " + id + " do Pedido id: " + pedido.getId() + ", teve o(s) item(ns) recebido(s).\n"
                                        + "Cupom Gerado ao Cliente e Troca finalizada com sucesso!");
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Solicitação de Troca não encontrada pelo id: " + id);
        }
    }

    // solicitacoes troca - delete
    @DeleteMapping("/solicitacoestroca/delete/{id}") @RolesAllowed("ROLE_ADMIN")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> deleteTroca(@PathVariable Long id) {
        try {
            itemTrocaRepository.deleteItens(id);
            solicitacaoTrocaRepository.deleteById(id);

            return ResponseEntity.ok().body("Solicitação de Troca deletada com sucesso!");
        }
        catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.badRequest().body("Solicitação de Troca não encontrada pelo id: " + id);
        }
    }
}
