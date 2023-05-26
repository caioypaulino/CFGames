package com.project.cfgames.services;

import com.project.cfgames.clients.FreteClient;
import com.project.cfgames.clients.responses.FreteResponse;
import com.project.cfgames.entities.Cupom;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.enums.StatusPedido;
import com.project.cfgames.entities.relations.CartaoPedido;
import com.project.cfgames.repositories.CupomRepository;
import com.project.cfgames.repositories.PedidoRepository;
import gherkin.lexer.De;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class PedidoService {
    @Autowired
    FreteClient freteClient;
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    EstoqueService estoqueService;
    @Autowired
    CupomRepository cupomRepository;
    @Autowired
    DataService dataService;


    // retorna valor total da compra (valor total carrinho + valor frete)
    public Float calcularValorTotal(Float valorCarrinho, Float valorFrete){
        return (valorCarrinho + valorFrete);
    }

    // setter de ValorParcial no cartão pedido
    public void valorParcial(Pedido pedido) {
        Float valorTotal = pedido.getValorTotal();
        Set<CartaoPedido> cartoesPedido = pedido.getCartoes();
        Set<Cupom> cupons = pedido.getCupons();

        for (Cupom cupom : cupons) {
            cupom = cupomRepository.getReferenceById(cupom.getCodigoCupom());

            valorTotal -= cupom.getValorDesconto();
        }

        for (CartaoPedido cartao : cartoesPedido) {
            cartao.setValorParcial(valorTotal / cartoesPedido.size());
        }
    }

    // calcula o valor das parcelas de cada cartão e atribui
    public void valorParcelas(Set<CartaoPedido> cartoesPedido) {
        for (CartaoPedido cartao : cartoesPedido) {
            cartao.setValorParcelas(cartao.getValorParcial() / cartao.getParcelas());
        }
    }

    // setter de Id Pedido em cada cartão do pedido
    public void idPedido(Pedido pedido, Set<CartaoPedido> cartoesPedido) {
        for (CartaoPedido cartao : cartoesPedido) {
            cartao.setPedido(pedido);
        }
    }

    // calcula frete através da feign client api (FreteClient) e gera uma responseDTO (FreteResponse)
    public FreteResponse calcularFrete(String cepDestino, Integer peso) {
        return freteClient.getFrete(cepDestino, peso);
    }

    // validar pagamento (+baixa no estoque)
    public ResponseEntity<String> verificarPagamento(Pedido pedido) {
        pedido.setStatus((pedido.getCupons().isEmpty() && pedido.getCartoes().isEmpty()) ? StatusPedido.PAGAMENTO_REPROVADO : StatusPedido.PAGAMENTO_APROVADO);
        pedido.setDataAtualizacao(dataService.getDateTimeNow());

        pedidoRepository.save(pedido);

        if (pedido.getStatus() == StatusPedido.PAGAMENTO_APROVADO) {
            // baixa no estoque para cada produto em pedido
            estoqueService.baixarEstoque(pedido.getCarrinhoCompra().getItens());

            return ResponseEntity.ok("Pagamento aprovado com sucesso!");
        }
        else {
            return ResponseEntity.badRequest().body("Pagamento reprovado.");
        }
    }
}
