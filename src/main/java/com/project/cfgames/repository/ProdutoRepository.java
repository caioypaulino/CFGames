package com.project.cfgames.repository;

import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}