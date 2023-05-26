package com.project.cfgames.repositories;

import com.project.cfgames.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Transactional
    @Query(value = "SELECT * FROM public.produtos WHERE titulo LIKE %?%", nativeQuery = true)
    List<Produto> findByTitulo(String titulo);

    @Transactional
    @Query(value = "SELECT public.produtos.* FROM public.produtos JOIN categorias_produtos ON categorias_produtos.produto_id = produtos.produto_id JOIN categorias ON categorias.categoria_id = categorias_produtos.categoria_id WHERE categorias.categoria_id IN :categorias GROUP BY produtos.produto_id", nativeQuery = true)
    List<Produto> findByCategorias(@Param("categorias") List<Long> categorias);

    @Transactional
    @Query(value = "SELECT public.produtos.* FROM public.produtos WHERE produtos.plataforma IN :plataformas", nativeQuery = true)
    List<Produto> findByPlataformas(@Param("plataformas") List<Long> plataformas);

    @Transactional
    @Query(value = "SELECT * FROM public.produtos ORDER BY preco ASC", nativeQuery = true)
    List<Produto> findByPrecoAsc();
    @Transactional
    @Query(value = "SELECT * FROM public.produtos ORDER BY preco DESC", nativeQuery = true)
    List<Produto> findByPrecoDesc();

    @Transactional
    @Query(value = "SELECT * FROM public.produtos ORDER BY data_lancamento DESC", nativeQuery = true)
    List<Produto> findByDataLancamentoDesc();

    @Transactional
    @Query(value = "SELECT * FROM public.produtos WHERE codigo_barras = ?", nativeQuery = true)
    Produto selectByCodigoBarras(String codigoBarras);
    @Transactional
    @Query(value = "SELECT * FROM public.produtos WHERE produto_id != ? AND codigo_barras = ? ", nativeQuery = true)
    Produto selectByCodigoBarrasUpdate(Long id, String codigoBarras);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.categorias_produtos WHERE produto_id = ? AND categoria_id = ?", nativeQuery = true)
    void removeCategoria(Long id, Long categoriaId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.categorias_produtos WHERE produto_id = ?", nativeQuery = true)
    void removeAllCategorias(Long id);
}