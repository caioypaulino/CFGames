package com.project.cfgames.repositories;

import com.project.cfgames.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

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
}