import React from "react";
import "./carrinho.css";
import Qtd_product from "../components/components_carrinho/qtd_produto/qtd_produto";
import Enderecos from "../components/components_carrinho/endereco/enderecos";

const jogos = [
    {
      nome: 'The Witcher 3: Wild Hunt',
      preco: 59.99,
      desenvolvedora: 'CD Projekt Red'
    },
    {
      nome: 'Red Dead Redemption 2',
      preco: 49.99,
      desenvolvedora: 'Rockstar Games'
    },
    {
      nome: 'Assassin\'s Creed Valhalla',
      preco: 69.99,
      desenvolvedora: 'Ubisoft'
    }
  ];


const Carrinho = (id) => {
  return (
    <div>
      <div className="resumoAndEnderecos">
        <Enderecos/>
      </div>
      <div className="listqtdProducts">
      {jogos.map((jogo) => (
        <Qtd_product // Use o componente QtdProduct (qtd_product) para representar cada jogo
          nameGame={jogo.nome}
          price={jogo.preco}
          developed={jogo.desenvolvedora}
        />
      ))}
      </div>
    </div>
  );
}

export default Carrinho;