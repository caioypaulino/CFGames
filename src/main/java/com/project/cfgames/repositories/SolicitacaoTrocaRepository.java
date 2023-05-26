package com.project.cfgames.repositories;


import com.project.cfgames.entities.Pedido;
import com.project.cfgames.entities.SolicitacaoTroca;
import com.project.cfgames.entities.enums.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SolicitacaoTrocaRepository extends JpaRepository<SolicitacaoTroca, Long> {
    @Transactional
    @Query(value = "SELECT * FROM public.solicitacoes_troca WHERE cliente_cliente_id = ?", nativeQuery = true)
    List<SolicitacaoTroca> selectSolicitacoesByCliente(Long clienteId);

    List<SolicitacaoTroca> findByStatus(StatusSolicitacao statusSolicitacao);
}
