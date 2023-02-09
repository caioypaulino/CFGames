package com.project.cfgames.repositories;

import com.project.cfgames.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Transactional
    @Query(value = "SELECT nome FROM public.categorias WHERE UPPER(nome) = UPPER(?)", nativeQuery = true)
    String selectCategoriaByName(String nomeCategoria);

    @Transactional
    @Query(value = "SELECT nome FROM public.categorias WHERE UPPER(nome) = UPPER(?) AND categoria_id != ?", nativeQuery = true)
    String selectCategoriaByNameAndId(String nomeCategoria, Long id);
}