package com.project.cfgames.controllers;

import com.project.cfgames.clients.responses.EnderecoResponse;
import com.project.cfgames.dtos.mappers.CustomMapper;
import com.project.cfgames.dtos.requests.*;
import com.project.cfgames.dtos.responses.CartaoClienteResponse;
import com.project.cfgames.dtos.responses.PerfilContaResponse;
import com.project.cfgames.dtos.responses.PerfilPessoalResponse;
import com.project.cfgames.entities.*;
import com.project.cfgames.entities.enums.StatusPedido;
import com.project.cfgames.entities.enums.StatusSolicitacao;
import com.project.cfgames.entities.relations.EnderecoCliente;
import com.project.cfgames.repositories.*;
import com.project.cfgames.services.*;
import com.project.cfgames.validations.*;
import feign.FeignException;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/perfil")
public class PerfilController {
    @Autowired
    ValidationCliente validationCliente;
    @Autowired
    ClienteService clienteService;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ValidationCartao validationCartao;
    @Autowired
    CartaoService cartaoService;
    @Autowired
    CartaoRepository cartaoRepository;
    @Autowired
    EnderecoService enderecoService;
    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    ValidationEnderecoCliente validationEnderecoCliente;
    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;
    @Autowired
    ValidationStatusPedido validationStatusPedido;
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ValidationPedido validationPedido;
    @Autowired
    ValidationSolicitacaoTroca validationSolicitacaoTroca;
    @Autowired
    ValidationStatusSolicitacao validationStatusSolicitacao;
    @Autowired
    SolicitacaoTrocaService solicitacaoTrocaService;
    @Autowired
    SolicitacaoTrocaRepository solicitacaoTrocaRepository;
    @Autowired
    CupomRepository cupomRepository;
    @Autowired
    EstoqueService estoqueService;
    @Autowired
    DataService dataService;
    @Autowired
    CupomService cupomService;


    // perfil - dados da conta
    @GetMapping("/conta")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> dadosContaCliente(@RequestHeader(value = "Authorization") String authToken) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        PerfilContaResponse dadosConta = new PerfilContaResponse();

        CustomMapper.update(cliente, dadosConta);

        return ResponseEntity.ok().body(dadosConta);
    }

    // perfil - dados pessoais
    @GetMapping("/pessoal")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> dadosPessoaisCliente(@RequestHeader(value = "Authorization") String authToken) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        PerfilPessoalResponse dadosPessoais = new PerfilPessoalResponse();

        CustomMapper.update(cliente, dadosPessoais);

        return ResponseEntity.ok().body(dadosPessoais);
    }

    // perfil - endereços
    @GetMapping("/enderecos")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> enderecosCliente(@RequestHeader(value = "Authorization") String authToken) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        Set<EnderecoCliente> enderecosCliente = cliente.getEnderecos();

        return ResponseEntity.ok().body(enderecosCliente);
    }

    // perfil - cartões
    @GetMapping("/cartoes")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> cartoesCliente(@RequestHeader(value = "Authorization") String authToken) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        List<CartaoClienteResponse> cartoes = new ArrayList<>();

        for (Cartao cartaoCliente : cliente.getCartoes()) {
            CartaoClienteResponse cartao = new CartaoClienteResponse();

            CustomMapper.update(cartaoCliente, cartao);

            cartoes.add(cartao);
        }

        return ResponseEntity.ok().body(cartoes);
    }

    // perfil - pedidos
    @GetMapping("/pedidos")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> pedidosCliente(@RequestHeader(value = "Authorization") String authToken) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        List<Pedido> pedidos = pedidoRepository.selectPedidosByCliente(cliente.getId());

        return ResponseEntity.ok().body(pedidos);
    }

    // perfil - solicitações troca
    @GetMapping("/solicitacoestroca")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> solicitacoesTrocaCliente(@RequestHeader(value = "Authorization") String authToken) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        List<SolicitacaoTroca> solicitacoes = solicitacaoTrocaRepository.selectSolicitacoesByCliente(cliente.getId());

        return ResponseEntity.ok().body(solicitacoes);
    }

    // perfil - cupons
    @GetMapping("/cupons")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> cuponsCliente(@RequestHeader(value = "Authorization") String authToken) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        List<Cupom> cupons = cupomRepository.selectCuponsByCliente(cliente.getId());

        return ResponseEntity.ok().body(cupons);
    }

    // adicionar endereco
    @PostMapping("/add/endereco")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> addEndereco(@RequestHeader(value = "Authorization") String authToken, @RequestBody @Valid EnderecoCliente enderecoCliente) {
        try {
            Cliente cliente = clienteService.getClienteByToken(authToken);

            enderecoCliente.setCliente(cliente);

            validationEnderecoCliente.allValidates(enderecoCliente);

            if (enderecoRepository.findById(enderecoCliente.getEndereco().getCep()).isEmpty()) {
                EnderecoResponse enderecoResponse = enderecoService.buscarCep(enderecoCliente.getEndereco().getCep());
                Endereco endereco = new Endereco(enderecoResponse, "Brasil");

                enderecoRepository.save(endereco);
            }

            enderecoClienteRepository.save(enderecoCliente);

            return ResponseEntity.ok().body(enderecoCliente.getId());
        }
        catch (FeignException ex) {
            return ResponseEntity.badRequest().body("Cep inválido.");
        }
        catch (InvalidDataAccessApiUsageException ex) {
            return ResponseEntity.badRequest().body("Cliente ou Cep null.");
        }
    }

    // adicionar cartao
    @PostMapping("/add/cartao")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> addCartao(@RequestHeader(value = "Authorization") String authToken, @RequestBody @Valid Cartao cartao) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        validationCartao.allValidates(cartao);
        validationCartao.cartaoClienteValidate(cliente, cartao);

        cartao.setBandeira(cartaoService.bandeiraCartao(cartao));
        cartaoRepository.save(cartao);

        Cartao cartaoReference = cartaoRepository.getReferenceById(cartao.getNumeroCartao());

        cliente.addCartoesCliente(cartaoReference);
        clienteRepository.save(cliente);

        return ResponseEntity.ok().body(cartao.getNumeroCartao());
    }

    // solicitar troca
    @PostMapping("/add/solicitacaotroca")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> solicitarTroca(@RequestHeader(value = "Authorization") String authToken, @RequestBody @Valid SolicitacaoTroca solicitacao) {
        try {
            Cliente cliente = clienteService.getClienteByToken(authToken);
            Pedido pedido = pedidoRepository.getReferenceById(solicitacao.getPedido().getId());

            solicitacao.setCliente(cliente);

            validationSolicitacaoTroca.allValidates(solicitacao, pedido);

            solicitacao.setStatus(StatusSolicitacao.PENDENTE);
            solicitacao.setDataAtualizacao(dataService.getDateTimeNow());
            solicitacao.setData(dataService.getDateTimeNow());

            pedido.setStatus(StatusPedido.TROCA_SOLICITADA);
            pedido.setDataAtualizacao(dataService.getDateTimeNow());

            solicitacaoTrocaRepository.save(solicitacao);
            pedidoRepository.save(pedido);

            return ResponseEntity.ok().body("Troca solicitada com sucesso!");
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Pedido não encontrado pelo id: " + solicitacao.getPedido().getId());
        }
    }

    // alterar email
    @PutMapping("/update/email")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> updateEmail(@RequestHeader(value = "Authorization") String authToken, @RequestBody @Valid EmailRequest request) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        validationCliente.updateEmailValidate(request, cliente);

        CustomMapper.update(request, cliente);

        clienteRepository.save(cliente);

        return ResponseEntity.ok().body("E-mail atualizado com sucesso!");
    }

    // alterar senha
    @PutMapping("/update/senha")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> updateSenha(@RequestHeader(value = "Authorization") String authToken, @RequestBody @Valid SenhaRequest request) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        validationCliente.updateSenhaValidate(cliente, request);

        CustomMapper.update(request, cliente);

        cliente.setSenha(clienteService.bCryptSenha(cliente));

        clienteRepository.save(cliente);

        return ResponseEntity.ok().body("Senha atualizada com sucesso!");
    }

    // alterar dados pessoais
    @PutMapping("/update/pessoal")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> updateDadosPessoais(@RequestHeader(value = "Authorization") String authToken, @RequestBody @Valid DadosPessoaisRequest request) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        validationCliente.updateIdadeValidate(request);

        CustomMapper.update(request, cliente);

        clienteRepository.save(cliente);

        return ResponseEntity.ok().body("Dados Pessoais atualizados com sucesso!");
    }

    // alterar endereço
    @PutMapping("/update/endereco/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> updateEnderecoCliente(@RequestHeader(value = "Authorization") String authToken, @PathVariable Long id, @RequestBody @Valid EnderecoClienteRequest request) {
        try {
            Cliente cliente = clienteService.getClienteByToken(authToken);
            EnderecoCliente enderecoCliente = enderecoClienteRepository.getReferenceById(id);

            validationEnderecoCliente.pertenceValidate(cliente, enderecoCliente);
            validationEnderecoCliente.updateValidate(cliente, request, id);

            if (request.getEndereco() != null && enderecoRepository.findById(request.getEndereco().getCep()).isEmpty()) {
                EnderecoResponse enderecoResponse = enderecoService.buscarCep(request.getEndereco().getCep());
                Endereco endereco = new Endereco(enderecoResponse, "Brasil");

                enderecoRepository.save(endereco);
            }

            CustomMapper.update(request, enderecoCliente);

            enderecoClienteRepository.save(enderecoCliente);

            return ResponseEntity.ok().body("Endereço Cliente atualizado com sucesso!");
        }
        catch (FeignException ex ) {
            return ResponseEntity.badRequest().body("Cep inválido.");
        }
        catch (EntityNotFoundException | MappingException ex) {
            return ResponseEntity.badRequest().body("Endereço Cliente não encontrado pelo id: " + id);
        }
    }

    // confirmar envio itens troca
    @PutMapping("/enviar/solicitacaotroca/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> enviarItensTroca(@RequestHeader(value = "Authorization") String authToken, @PathVariable Long id) {
        try {
            Cliente cliente = clienteService.getClienteByToken(authToken);
            SolicitacaoTroca solicitacaoTroca = solicitacaoTrocaRepository.getReferenceById(id);
            Pedido pedido = solicitacaoTroca.getPedido();

            validationStatusSolicitacao.clienteSolicitacaoValidate(cliente, solicitacaoTroca);
            solicitacaoTrocaService.updateStatusTroca(solicitacaoTroca, pedido, StatusSolicitacao.PRODUTOS_ENVIADOS, StatusPedido.PRODUTOS_TROCA_ENVIADOS);

            return ResponseEntity.ok("Envio do(s) item(ns) solicitação confirmado com sucesso!");
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Solicitação de Troca não encontrada pelo id: " + id);
        }
    }

    // remover endereco
    @DeleteMapping("/remove/endereco")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> removeEndereco(@RequestHeader(value = "Authorization") String authToken, @RequestBody @Valid IdRequest request) {
        try {
            Cliente cliente = clienteService.getClienteByToken(authToken);
            EnderecoCliente enderecoCliente = enderecoClienteRepository.getReferenceById(request.getId());

            validationEnderecoCliente.pertenceValidate(cliente, enderecoCliente);

            // verifica se endereço cliente está relacionado a um pedido
            if (!pedidoRepository.selectPedidosByEndereco(enderecoCliente.getId()).isEmpty()) {
                // Apenas removendo cliente de endereço cliente, mas não deletando o registro
                enderecoCliente.setCliente(null);

                enderecoClienteRepository.save(enderecoCliente);

                return ResponseEntity.ok("Endereço removido com sucesso!");
            }

            enderecoClienteRepository.deleteById(request.getId());

            return ResponseEntity.ok("Endereço removido com sucesso!");
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Endereço Cliente não encontrado pelo id: " + request.getId());
        }
    }

    // remover cartao
    @DeleteMapping("/remove/cartao")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> removeCartao(@RequestHeader(value = "Authorization") String authToken, @RequestBody Cartao cartao) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        if (cartaoRepository.selectCartaoCliente(cliente.getId(), cartao.getNumeroCartao()) != null) {
            cartaoRepository.removeCartao(cliente.getId(), cartao.getNumeroCartao());

            return ResponseEntity.ok("Cartão removido com sucesso!");
        }
        else {
            return ResponseEntity.badRequest().body("Cartão não encontrado pelo numeroCartao: " + cartao.getNumeroCartao());
        }
    }

    // remover cartões
    @DeleteMapping("/remove/cartoes")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void removeCartoes(@RequestHeader(value = "Authorization") String authToken, @RequestBody Set<Cartao> cartoes) {
        for (Cartao cartao : cartoes) {
            removeCartao(authToken, cartao);
        }
    }

    // cancelar pedido
    @DeleteMapping("/cancel/pedido/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> cancelarPedido(@RequestHeader(value = "Authorization") String authToken, @PathVariable Long id) {
        try {
            Cliente cliente = clienteService.getClienteByToken(authToken);
            Pedido pedido = pedidoRepository.getReferenceById(id);

            validationStatusPedido.cancelamentoValidate(cliente, pedido, StatusPedido.CANCELADO);

            if (pedido.getStatus() == StatusPedido.PAGAMENTO_APROVADO) {
                estoqueService.reporEstoqueCancelamento(pedido.getCarrinhoCompra().getItens());
            }

            pedido.setStatus(StatusPedido.CANCELADO);
            pedido.setDataAtualizacao(dataService.getDateTimeNow());

            // redisponibilizando possíveis cupons utilizados na compra cancelada gerando novo cupom
            // pedido possui cupons e cartões : pedido possui cupons e não possui cartões
            if (!pedido.getCupons().isEmpty() && !pedido.getCartoes().isEmpty()) {
                Float valorCupons = 0f;

                for (Cupom cupom : pedido.getCupons()) {
                    valorCupons += cupom.getValorDesconto();
                }

                cupomService.gerarCupom(cliente, valorCupons);
            }
            else if (!pedido.getCupons().isEmpty()){
                cupomService.gerarCupom(cliente, pedido.getValorTotal());
            }
            else {

            }

            pedidoRepository.save(pedido);

            return ResponseEntity.ok("Pedido cancelado com sucesso!");
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Pedido não encontrado pelo id: " + id);
        }
    }

    // cancelar troca
    @DeleteMapping("/cancel/solicitacaotroca/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> cancelarTroca(@RequestHeader(value = "Authorization") String authToken, @PathVariable Long id) {
        try {
            Cliente cliente = clienteService.getClienteByToken(authToken);
            SolicitacaoTroca solicitacaoTroca = solicitacaoTrocaRepository.getReferenceById(id);
            Pedido pedido = solicitacaoTroca.getPedido();

            validationStatusSolicitacao.clienteSolicitacaoValidate(cliente, solicitacaoTroca);
            solicitacaoTrocaService.updateStatusTroca(solicitacaoTroca, pedido, StatusSolicitacao.CANCELADA, StatusPedido.TROCA_CANCELADA);

            return ResponseEntity.ok("Troca cancelada com sucesso!");
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("Solicitação de Troca não encontrada pelo id: " + id);
        }
    }

    // desativar perfil
    @DeleteMapping("/disable")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> desativarPerfil(@RequestHeader(value = "Authorization") String authToken) {
        Cliente cliente = clienteService.getClienteByToken(authToken);

        // missing work

        return ResponseEntity.ok("Perfil desativado com sucesso!");
    }

    // handler Enum type Json exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String handleCustomValidationsExceptions (){
        return "Tipo inválido. Exemplo:<br>\n0- ENTREGA,\n<br>1- COBRANCA,\n<br>2- RESIDENCIAL,\n<br> 3- GERAL.";
    }
}
