package com.project.cfgames.validations;

import com.project.cfgames.entities.CarrinhoCompra;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.enums.TipoEndereco;
import com.project.cfgames.entities.relations.CartaoPedido;
import com.project.cfgames.entities.relations.EnderecoCliente;
import com.project.cfgames.repositories.CarrinhoCompraRepository;
import com.project.cfgames.repositories.CartaoRepository;
import com.project.cfgames.repositories.EnderecoClienteRepository;
import com.project.cfgames.repositories.PedidoRepository;
import com.project.cfgames.validations.exceptions.CustomValidationException;
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
    CarrinhoCompraRepository carrinhoCompraRepository;
    @Autowired
    CartaoRepository cartaoRepository;

    // valida cartoes
    @SneakyThrows
    public void cartoesValidate(Pedido pedido) {
        Set<CartaoPedido> cartoesPedido = pedido.getCartoes();

        if (cartoesPedido.size() > 2) {
            throw new CustomValidationException("Quantidade de Cartões Inválido.(Max = 2)");
        }
        else {
            for(CartaoPedido cartao : cartoesPedido) {
                if (cartao.getCartao().getNumeroCartao() == null) {
                    throw new CustomValidationException("Número Cartão null.");
                }
                else if (cartaoRepository.findById(cartao.getCartao().getNumeroCartao()).isEmpty()) {
                    throw new CustomValidationException("Número Cartão não encontrado. Número Cartão: " + cartao.getCartao().getNumeroCartao());
                }
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
        Set<CartaoPedido> cartoesPedido = pedido.getCartoes();
        float totalParcial = 0f;

        for (CartaoPedido cartao : cartoesPedido) {
            totalParcial += cartao.getValorParcial();
        }

        if (!Objects.equals(pedido.getValorTotal(), totalParcial)) {
            throw new CustomValidationException("Valores Parciais dos Cartões inválido.(Soma Total = R$ " + totalParcial + " diferente de Total Pedido = R$" + pedido.getValorTotal() + ")");
        }
    }

    public void allValidates(Pedido pedido) {
        cartoesValidate(pedido);
        enderecoClienteValidate(pedido);
        carrinhoClienteValidate(pedido);
    }
}
