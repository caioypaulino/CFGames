package com.project.cfgames.repositories;


import com.project.cfgames.entities.Cupom;
import com.project.cfgames.entities.SolicitacaoTroca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, String> {
    @Transactional
    @Query(value = "SELECT * FROM public.cupons WHERE cliente_cliente_id = ?", nativeQuery = true)
    List<Cupom> selectCuponsByCliente(Long clienteId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.cupons_pedidos WHERE cupons_codigo_cupom = ?", nativeQuery = true)
    void removePedido(String codigoCupom);
}
