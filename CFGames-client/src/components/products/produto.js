import React from "react";
import "./produto.css";

function product(props) {

  const { image, nome, preco, desenvolvedora } = props;

  return(
    <form className="productGame">
        <div dangerouslySetInnerHTML={{ __html: image }} />
        <p className="nameGame">{nome}</p>
        <p className="developed">{desenvolvedora}</p>
        <p className="price">R$ {preco}</p>
        <input className="btnComprar" value="Comprar" type="submit"/>
    </form>
  );
}

export default product;
