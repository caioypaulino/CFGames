import image_product from "../../../assets/products/image 3.svg";
import "./qtd_produto.css";
import polygon_icon from "../../../assets/buttons/Polygon 14.svg";
import React, { useState } from "react";

const qtd_product = (props) => {
    const [valor, setValor] = useState(1);
    const { nameGame, price, developed } = props;
  
    const incrementar = () => {
      setValor((prevValor) => prevValor + 1);
    };
  
    const decrementar = () => {
      if (valor > 1) {
        setValor((prevValor) => prevValor - 1);
      }
    };
  
    return (
      <div className="qtd_product">
        <img className="productImageCart" src={image_product} alt="product1" />
        <div className="productInfo">
          <p className="nameGame">{nameGame}</p>
          <p>{developed}</p>
          <p className="price">R$ {price}</p>
        </div>
        <div className="qtdChange">
          <p>Quantidade</p>
          <button
            className="btnMinus"
            onClick={decrementar}
            disabled={valor === 1}
          >
            <img className="polygon" src={polygon_icon} />
          </button>
          <p>{valor}</p>
          <button className="btnPlus" onClick={incrementar}>
            <img className="polygon" src={polygon_icon} />
          </button>
        </div>
        <form>
          <input className="removeFromCart" type="submit" value="Remover" />
        </form>
      </div>
    );
  };
  
  export default qtd_product;
