package com.project.cfgames.services.admin;

import com.project.cfgames.dtos.responses.GraficoCategoriaResponse;
import com.project.cfgames.dtos.responses.GraficoProdutoResponse;
import com.project.cfgames.dtos.responses.StatsResponse;
import com.project.cfgames.entities.Categoria;
import com.project.cfgames.entities.Produto;
import com.project.cfgames.repositories.CategoriaRepository;
import com.project.cfgames.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminGraficoService {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    public List<?> createGraficoResponse(LocalDateTime dataInicio, LocalDateTime dataFim, boolean isProduto) {
        // Map para as estatísticas por produto ou categoria
        Map<Long, List<StatsResponse>> statsPorItem = new HashMap<>();

        // Percorre cada mês entre dataInicio e dataFim
        LocalDateTime dataAtual = dataInicio;
        while (!dataAtual.isAfter(dataFim)) {
            // Calcula o próximo mês
            LocalDateTime proximoMes = dataAtual.plusMonths(1);

            // Consulta estatísticas para o mês atual
            List<Object[]> statsDb = isProduto ?
                    produtoRepository.selectStatsProdutos(dataAtual, proximoMes) :
                    categoriaRepository.selectStatsCategorias(dataAtual, proximoMes);

            // Convertendo cada resultado da query em DTO e adicionando ao map
            for (Object[] stats : statsDb) {
                Long id = ((BigInteger) stats[0]).longValue();
                Integer quantidadeVendida = ((BigInteger) stats[1]).intValue();
                Float valorTotal = (Float) stats[2];

                // Verificando se o item já está no map
                if (!statsPorItem.containsKey(id)) {
                    statsPorItem.put(id, new ArrayList<>());
                }

                // Criando um objeto com os valores do item e mês/ano
                StatsResponse statsResponse = new StatsResponse(
                        quantidadeVendida,
                        valorTotal,
                        dataAtual.getMonthValue(),
                        dataAtual.getYear()
                );

                // Adiciona as estatísticas do item ao map
                statsPorItem.get(id).add(statsResponse);
            }

            // Avançando para o próximo mês
            dataAtual = proximoMes;
        }

        // Resposta final
        List<Object> graficoResponses = new ArrayList<>();

        for (Map.Entry<Long, List<StatsResponse>> entry : statsPorItem.entrySet()) {
            Long itemId = entry.getKey();
            List<StatsResponse> statsItem = entry.getValue();

            Object item = isProduto ?
                    produtoRepository.getReferenceById(itemId) :
                    categoriaRepository.getReferenceById(itemId);

            // Cria o objeto de resposta adequado com os stats do item
            if (isProduto) {
                graficoResponses.add(new GraficoProdutoResponse((Produto) item, statsItem));
            }
            else {
                graficoResponses.add(new GraficoCategoriaResponse((Categoria) item, statsItem));
            }
        }

        return graficoResponses;
    }
}
