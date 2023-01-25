package com.project.cfgames.repositories;

import com.project.cfgames.entities.Endereco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, String> {
    @Transactional
    @Query(value = "SELECT cep FROM public.enderecos WHERE cep = ?", nativeQuery = true)
    String selectEnderecoCep(String cep);
}