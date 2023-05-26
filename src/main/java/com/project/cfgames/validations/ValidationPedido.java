package com.project.cfgames.validations;

import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.entities.Cupom;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.enums.TipoEndereco;
import com.project.cfgames.entities.relations.CartaoPedido;
import com.project.cfgames.entities.relations.EnderecoCliente;
import com.project.cfgames.exceptions.CustomValidationException;
import com.project.cfgames.repositories.*;
import com.project.cfgames.services.DataService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class ValidationPedido {
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;
    @Autowired
    ValidationCarrinhoCompra validationCarrinhoCompra;
    @Autowired
    CarrinhoCompraRepository carrinhoCompraRepository;
    @Autowired
    CartaoRepository cartaoRepository;
    @Autowired
    CupomRepository cupomRepository;
    @Autowired
    DataService dataService;
    @Autowired
    ValidationCartao validationCartao;

    // valida pagamento
    @SneakyThrows
    public void pagamentoValidate(Pedido pedido) {
        if ((pedido.getCupons() == null || pedido.getCupons().isEmpty()) && (pedido.getCartoes() == null || pedido.getCartoes().isEmpty())) {
            throw new CustomValidationException("Pagamento Inválido. (Nenhum Cupom ou Cartão informado)");
        }
        else {
            if (pedido.getCupons() != null && !pedido.getCupons().isEmpty()) {
                cuponsValidate(pedido);
            }
            if (pedido.getCartoes() != null && !pedido.getCartoes().isEmpty()) {
                cartoesValidate(pedido);
            }
        }
    }

    // valida cupom
    @SneakyThrows
    public void cuponsValidate(Pedido pedido) {
        Set<Cupom> cuponsPedido = pedido.getCupons();

        for (Cupom cupom : cuponsPedido) {
            if (cupom.getCodigoCupom() == null || cupom.getCodigoCupom().isBlank()) {
                throw new CustomValidationException("Código Cupom null.");
            }
            else if (cupomRepository.findById(cupom.getCodigoCupom()).isEmpty()) {
                throw new CustomValidationException("Código Cupom não encontrado. Código: " + cupom.getCodigoCupom());
            }
            else {
                cupom = cupomRepository.getReferenceById(cupom.getCodigoCupom());

                if (!pedido.getCliente().equals(cupom.getCliente())) {
                    throw new CustomValidationException("Cupom não pertence ao cliente.");
                }
                else if (cupom.getValidade().isBefore(dataService.getDateTimeNow())) {
                    throw new CustomValidationException("Validade do Cupom Inválida.");
                }
                else if (!cupom.getDisponivel()) {
                    throw new CustomValidationException("Cupom Indisponível.");
                }
            }
        }
    }

    // valida cartoes
    @SneakyThrows
    public void cartoesValidate(Pedido pedido) {
        Set<CartaoPedido> cartoesPedido = pedido.getCartoes();

        for (CartaoPedido cartao : cartoesPedido) {
            if (cartao.getCartao().getNumeroCartao().isBlank()) {
                throw new CustomValidationException("Número Cartão vazio.");
            }
            else if (cartaoRepository.findById(cartao.getCartao().getNumeroCartao()).isEmpty()) {
                throw new CustomValidationException("Número Cartão não encontrado. Número Cartão: " + cartao.getCartao().getNumeroCartao());
            }
            else {
                validationCartao.vencimentoValidate(cartaoRepository.getReferenceById(cartao.getCartao().getNumeroCartao()));
            }
        }
    }

    // valida endereço cliente
    @SneakyThrows
    public void enderecoClienteValidate(Pedido pedido) {
        if (pedido.getEnderecoCliente().getId() == null) {
            throw new CustomValidationException("Endereço Cliente Id null.");
        }
        else {
            EnderecoCliente endereco = enderecoClienteRepository.getReferenceById(pedido.getEnderecoCliente().getId());

            if (!Objects.equals(endereco.getCliente().getId(), pedido.getCliente().getId())) {
                throw new CustomValidationException("Endereço não corresponde ao Cliente.");
            }
            else if (endereco.getTipo() == TipoEndereco.COBRANCA) {
                throw new CustomValidationException("Tipo Endereço Inválido.(Permitido tipo 0-ENTREGA ou 2-AMBOS)");
            }
        }
    }

    // valida carrinho de compra cliente
    @SneakyThrows
    public void carrinhoClienteValidate(Pedido pedido) {
        if (pedido.getCarrinhoCompra().getId() == null) {
            throw new CustomValidationException("Carrinho de Compra Id null.");
        }
        else {
            CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.getReferenceById(pedido.getCarrinhoCompra().getId());

            if (!Objects.equals(carrinhoCompra.getCliente().getId(), pedido.getCliente().getId())) {
                throw new CustomValidationException("Carrinho de Compra não corresponde ao Cliente.");
            }
            else if (pedidoRepository.selectPedidoByCarrinho(carrinhoCompra.getId()) != null) {
                throw new CustomValidationException("Carrinho de Compra já utilizado em um Pedido.");
            }
        }
    }

    // valida valor parcial caso tenha um cartao pedido sem valor parcial definido
    public boolean valorParcialNullValidate(Pedido pedido) {
        Set<CartaoPedido> cartoesPedido = pedido.getCartoes();

        for (CartaoPedido cartao : cartoesPedido) {
            return cartao.getValorParcial() == null;
        }

        return false;
    }

    // valida soma valor parcial
    @SneakyThrows
    public void valorParcialValidate(Pedido pedido) {
        float valorPagamento = 0f;

        Set<Cupom> cupons = pedido.getCupons();

        for (Cupom cupom : cupons) {
            cupom = cupomRepository.getReferenceById(cupom.getCodigoCupom());

            valorPagamento += cupom.getValorDesconto();
        }

        Set<CartaoPedido> cartoesPedido = pedido.getCartoes();

        for (CartaoPedido cartao : cartoesPedido) {
            valorPagamento += cartao.getValorParcial();
        }

        if (!Objects.equals(pedido.getValorTotal(), valorPagamento)) {
            String message = "Valor(es) Parcial(is) do(s) Cartão(ões) inválido(s).";

            if (!cupons.isEmpty()) {
                message += "(Soma Total com desconto(s) de Cupom(ns) = R$ " + valorPagamento + " diferente de Total Pedido = R$" + pedido.getValorTotal() + ")";
            }
            else {
                message += "(Soma Total = R$ " + valorPagamento + " diferente de Total Pedido = R$" + pedido.getValorTotal() + ")";
            }
            throw new CustomValidationException(message);
        }
    }

    // valida valor soma cupons e retorna true caso haja diferença positiva
    @SneakyThrows
    public Float valorCuponsValidate(Pedido pedido) {
        Float valorCupons = 0f;

        Set<Cupom> cupons = pedido.getCupons();

        for (Cupom cupom : cupons) {
            cupom = cupomRepository.getReferenceById(cupom.getCodigoCupom());

            valorCupons += cupom.getValorDesconto();
        }

        if (valorCupons < pedido.getValorTotal()){
            throw new CustomValidationException("Valor de desconto do(s) cupom(ns) insuficiente. (Soma de Cupom(ns) = R$ " + valorCupons + " é inferior ao Total Pedido = R$" + pedido.getValorTotal() + ")");
        }
        else if (valorCupons > pedido.getValorTotal()) {
            return valorCupons - pedido.getValorTotal();
        }

        return valorCupons;
    }

    public void allValidates(Pedido pedido) {
        pagamentoValidate(pedido);
        enderecoClienteValidate(pedido);
        carrinhoClienteValidate(pedido);
        validationCarrinhoCompra.quantidadeValidate(pedido.getCarrinhoCompra());
    }
}
