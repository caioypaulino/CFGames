package com.project.cfgames.services;

import com.project.cfgames.clients.FreteClient;
import com.project.cfgames.clients.requests.AddressRequest;
import com.project.cfgames.clients.requests.FreteRequest;
import com.project.cfgames.clients.requests.PackageRequest;
import com.project.cfgames.clients.responses.FreteResponse;
import com.project.cfgames.entities.Cupom;
import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.enums.StatusPedido;
import com.project.cfgames.entities.relations.CartaoPedido;
import com.project.cfgames.repositories.CupomRepository;
import com.project.cfgames.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
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

        Float valorParcial = valorTotal / cartoesPedido.size();

        for (CartaoPedido cartao : cartoesPedido) {
            cartao.setValorParcial(valorParcial);
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

    // definindo todos os parâmetros de FreteRequest para cálculo de frete
    private static FreteRequest setFreteRequest(String cepDestino, double peso) {
        FreteRequest request = new FreteRequest();
        AddressRequest fromAddress = new AddressRequest();
        AddressRequest toAddress = new AddressRequest();
        PackageRequest pacote = new PackageRequest();

        fromAddress.setPostal_code("08773600");
        toAddress.setPostal_code(cepDestino);

        pacote.setHeight(30.0); // altura do pacote
        pacote.setWidth(30.0); // largura do pacote
        pacote.setLength(30.0); // comprimento do pacote
        pacote.setWeight(peso / 1000); // peso em kg

        request.setFrom(fromAddress);
        request.setTo(toAddress);
        request.setPackageRequest(pacote);

        return request;
    }

    // calcula frete através da feign client api (FreteClient) e gera uma responseDTO (FreteResponse)
    public FreteResponse calcularFrete(String cepDestino, Integer peso) {
        String authorizationHeader = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNjQ2MjQ0NDc2Zjc0ODhiYTNiMDMyYzkxZjhmM2YyNzVmYjQ1OTU4ZjE4NTM4Y2IwZjc5ODU3Yzg1NTgyNjgxNmRiYTQ1YjQ0YzZmMmIzMmUiLCJpYXQiOjE3MTIyNDY4NjUuMjU3ODE1LCJuYmYiOjE3MTIyNDY4NjUuMjU3ODE3LCJleHAiOjE3NDM3ODI4NjUuMjM2MDE3LCJzdWIiOiI5YmJhM2Q1NS01MDg1LTRlZmMtODg0My00MjkxMThkNGU2M2EiLCJzY29wZXMiOlsic2hpcHBpbmctY2FsY3VsYXRlIl19.Czbwovhl85UXkk5zaVN-ewJqe_BBBzgqUTvz96ZlckmGi0bYKXmwZioWLIisHXW3RO_XPaWum7ulbIEyJSZkrT0Fu24K8quH4o-m5fxNJjLqmi1XpDQWYPVIRIouAOQIGJseZiyFiYJK-DBkcrOcOog6bNb0_8-Mfau0Jd7sa8oInH5N-EDSKtqR6m-j_Yo7uGtAXsCZW64DMq0LjGahp-Ns46HbhrKWft5oxVCksxq7k_AHQYESyHnXA_2mxcKp6fG13BTQxCgGG4OehFK-_3zsE41BhJa4as4TUvrq422QHLlw8JzEsD5TwMH40vxJft4M7DJuE6ZbSC-1IIxR_o6hkQ0Fhzj8wCTLCVH7Vnp0D3EwtePsinVrkWKXgSP1CZMX7GXJwgIY6pi71Vv0OSxIz7Xi11qH8HcwV425DItwg98smBmLOi6OxgJdO-nY4aNBO-YO-dO7on_Mb5CBNFHFODA593PB0Eyqa_8jWWrKdv_GIcr2EUYpk-yALkH4B1sxJU7P5RQQFGrJQOV2TBLU0-KPFm3A4GIBjtgdqGyTK_eOI8TlS1IKo9FglCsXFENit1vBRpfdxu-U8A0hmhDsou_vyONXqNf5Rzunu6zJI29ZGn4L677yk7UwKNf6nBFqjJAU4XystmsdugbXDJSGmB6CTT6aEpWu_Y4ZX1c";
        String userAgentHeader = "Aplicação caiohenriquepaulino@gmail.com";

        FreteRequest request = setFreteRequest(cepDestino, (double) peso);

        List<FreteResponse> fretes = freteClient.getFrete(request, authorizationHeader, userAgentHeader);

        return melhorFrete(fretes);
    }

    // seleciona o melhor frete (mais barato e com prazo válido)
    public FreteResponse melhorFrete(List<FreteResponse> fretes) {
        return fretes.stream()
            // Filtrar fretes com preço não nulo e prazo de entrega não nulo
            .filter(frete -> frete.getPrice() != null && frete.getDeliveryTime() > 0)
            // Encontrar o frete com o menor preço
            .min(Comparator.comparingDouble(frete -> Double.parseDouble(frete.getPrice())))
            .orElse(null);
    }

    // validar pagamento (+baixa no estoque)
    public ResponseEntity<String> verificarPagamento(Pedido pedido) {
        pedido.setStatus((pedido.getCupons().isEmpty() && pedido.getCartoes().isEmpty()) ? StatusPedido.PAGAMENTO_REPROVADO : StatusPedido.PAGAMENTO_APROVADO);
        pedido.setDataAtualizacao(dataService.getDateTimeNow());

        pedidoRepository.save(pedido);

        if (pedido.getStatus() == StatusPedido.PAGAMENTO_APROVADO) {
            // baixa no estoque para cada produto em pedido
            estoqueService.baixarEstoque(pedido.getCarrinhoCompra().getItens());

            return ResponseEntity.ok("Pagamento aprovado!");
        }
        else {
            return ResponseEntity.badRequest().body("Pagamento reprovado.");
        }
    }
}
