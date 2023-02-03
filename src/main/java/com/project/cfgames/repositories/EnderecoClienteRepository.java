package com.project.cfgames.repositories;

import com.project.cfgames.clients.entities.relations.EnderecoCliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EnderecoClienteRepository extends JpaRepository<EnderecoCliente, Long> {
    @Transactional
    @Query(value = "SELECT endereco_cliente_id FROM public.enderecos_clientes WHERE numero = ? AND cliente_cliente_id = ? AND endereco_cep = ?", nativeQuery = true)
    List<Long> selectEnderecoCliente(String numero, Long clienteId, String cep);

}