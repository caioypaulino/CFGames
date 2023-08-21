import React from "react";
import style from "./Produto.module.css";

function product(props) {

  const { image, nome, preco, desenvolvedora, id } = props;

  const salvarNoCarrinho = (produto) => {
    console.log(produto);
    const carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    carrinho.push(produto);
    localStorage.setItem('carrinho', JSON.stringify(carrinho));
  };

  return(
    <div className={style.productGame}>
        <div dangerouslySetInnerHTML={{ __html: image }} />
        <p className={style.paragraph}>{nome}</p>
        <p className={style.paragraph}>{desenvolvedora}</p>
        <p className={style.price}>R$ {preco}</p>
        <input className={style.btnComprar} value="Comprar" type="submit" onClick={() => salvarNoCarrinho({
          id,
          svgString: image,
          nome,
          preco,
          desenvolvedora,
        })}/>
    </div>
  );
}

export default product;
