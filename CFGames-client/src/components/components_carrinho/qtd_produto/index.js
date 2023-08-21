import style from "./Qtd_produto.module.css";
import polygon_icon from "../../../assets/buttons/Polygon 14.svg";
import React, { useState } from "react";

const qtd_product = (props) => {
  const [valor, setValor] = useState(1);
  const { nameGame, price, developed, id, removeFromCart, image } = props;

  const incrementar = () => {
    setValor((prevValor) => prevValor + 1);
  };

  const decrementar = () => {
    if (valor > 1) {
      setValor((prevValor) => prevValor - 1);
    }
  };

  return (
    <div className={style.qtd_product}>
      <div
        className={style.productImageCart}
        dangerouslySetInnerHTML={{ __html: image }}
      />
      <div className={style.productInfo}>
        <p className={style.nameGame}>{nameGame}</p>
        <p>{developed}</p>
        <p className={style.price}>R$ {price}</p>
      </div>
      <div className={style.qtdChange}>
        <p>Quantidade</p>
        <button
          className={style.btnMinus}
          onClick={decrementar}
          disabled={valor === 1}
        >
          <img className={style.polygon} src={polygon_icon} />
        </button>
        <p>{valor}</p>
        <button className={style.btnPlus} onClick={incrementar}>
          <img className={style.polygon} src={polygon_icon} />
        </button>
      </div>
    </div>
  );
};

export default qtd_product;
