package com.project.cfgames.repositories;

import com.project.cfgames.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Transactional
    @Query(value = "SELECT * FROM public.produtos WHERE codigo_barras = ?", nativeQuery = true)
    Produto findByCodigoBarras(String codigoBarras);
    @Transactional
    @Query(value = "SELECT * FROM public.produtos WHERE produto_id != ? AND codigo_barras = ? ", nativeQuery = true)
    Produto findByIdAndCodigoBarras(Long id, String codigoBarras);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.categorias_produtos WHERE produto_id = ?", nativeQuery = true)
    void removeAllCategorias(Long id);
}