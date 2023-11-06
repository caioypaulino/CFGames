package com.project.cfgames.repositories;

import com.project.cfgames.dtos.responses.GraficoResponse;
import com.project.cfgames.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Transactional
    @Query(value = "SELECT * FROM public.categorias WHERE UPPER(nome) = UPPER(?)", nativeQuery = true)
    Categoria selectCategoriaByName(String nomeCategoria);

    @Transactional
    @Query(value = "SELECT * FROM public.categorias WHERE UPPER(nome) = UPPER(?) AND categoria_id != ?", nativeQuery = true)
    Categoria selectCategoriaByNameAndId(String nomeCategoria, Long id);

    @Transactional
    @Query(value = "SELECT produto_id FROM public.categorias_produtos WHERE produto_id = ? AND categoria_id = ?", nativeQuery = true)
    Long selectCategoriaProduto(Long id, Long categoriaId);

    @Transactional
    @Query(value = "SELECT\n" +
            "    c.nome AS nome,\n" +
            "    SUM(ic.quantidade) AS quantidadeVendida,\n" +
            "    SUM(ic.valor_item) AS valorTotal\n" +
            "FROM\n" +
            "    categorias c\n" +
            "    JOIN categorias_produtos cp ON c.categoria_id = cp.categoria_id\n" +
            "    JOIN produtos p ON p.produto_id = cp.produto_id\n" +
            "    JOIN itens_carrinhos ic ON p.produto_id = ic.produto_produto_id\n" +
            "    JOIN carrinhos_compra cc ON ic.carrinho_compra_id = cc.carrinho_compra_id\n" +
            "    JOIN pedidos pe ON cc.carrinho_compra_id = pe.carrinho_compra_carrinho_compra_id\n" +
            "WHERE\n" +
            "        pe.data >= ? AND pe.data <= ?\n" +
            "GROUP BY\n" +
            "    c.nome\n" +
            "ORDER BY\n" +
            "    valorTotal DESC;", nativeQuery = true)
    List<Object[]> selectStatsCategorias(LocalDateTime dataInicio, LocalDateTime dataFim);
}