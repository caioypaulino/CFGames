import React from "react";
import styles from "./Carrinho.module.css";
import Qtd_product from "../../components/components_carrinho/qtd_produto";
import Enderecos from "../../components/components_carrinho/endereco";
import { useState, useEffect } from 'react';
import Resumo from "../../components/components_carrinho/resumo";

const Carrinho = (id) => {

  const localCarrinho = JSON.parse(localStorage.getItem('carrinho')) || [];

  const [jogos, setJogos] = useState(localCarrinho);

  // useEffect(() => {
  //   setJogos(localCarrinho);
  // }, [localCarrinho]);
  
  const removeFromCart = (id) => {
    const updatedCart = localCarrinho.filter((product) => product.id !== id);
    localStorage.setItem('carrinho', JSON.stringify(updatedCart));
    setJogos(updatedCart);
  }

  return (
    <div>
      <div className="resumoAndEnderecos">
        <Enderecos/>
      </div>
      <div className="">
        <Resumo
          price={jogos.reduce((accumulator, jogo) => accumulator + jogo.preco, 0)}
          frete={22}
        />
      </div>
      <div className="listqtdProducts">
      {jogos.map((jogo) => (
        <div className={styles.containerListProduct}>
          <Qtd_product // Use o componente QtdProduct (qtd_product) para representar cada jogo
            key={jogo.id}
            id={jogo.id}
            image={jogo.svgString}
            nameGame={jogo.nome}
            price={jogo.preco}
            developed={jogo.desenvolvedora}
            availability={jogo.availability}
          />
          <input className={styles.btnRemove} type="submit" value="Remover" onClick={() => removeFromCart(jogo.id)} />
        </div >
      ))}
      </div>
    </div>
  );
}

export default Carrinho;