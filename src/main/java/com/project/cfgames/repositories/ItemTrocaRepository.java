package com.project.cfgames.repositories;


import com.project.cfgames.entities.ItemTroca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ItemTrocaRepository extends JpaRepository<ItemTroca, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.itens_trocas WHERE solicitacao_troca_id = ?", nativeQuery = true)
    void deleteItens(Long solicitacaoTrocaId);
}
