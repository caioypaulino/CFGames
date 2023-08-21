import React, {useState, useEffect} from "react";
import style from "./Resumo.module.css";
import cuponsData from "../../../utils/cupons.json"

const resumo = (props) => {
  const [inputValue, setInputValue] = useState("");
  const [cupons, setProducts] = useState([]);

  useEffect(() => {
    setProducts(cuponsData);
  }, []);

  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  const [discount, setDiscount] = useState("");

  const verificaCupom = (codeToCheck) => {
    return cupons.some((cupom) => cupom.code === codeToCheck);
  };

  const aplicaDesconto = (code) => {
    if (verificaCupom(code)) {
      const cupomEncontrado = cupons.find((cupom) => cupom.code === code);
      if (cupomEncontrado) {
        setDiscount(cupomEncontrado.discount); // Supondo que você tem um state 'discount'
      } else {
        window.alert(`Cupom ${cupons} encontrado, mas inválido`); // Caso o cupom seja encontrado, mas não seja válido
      }
    } else {
        console.log(cupons)
      window.alert(`Cupom ${cupons} não encontrado ou inválido`);
    }
  };

  return (
    <>
      <div className={style.resumo}>
        <h1>Resumo</h1>
        <p>Valor: R${props.price.toFixed(2)}</p>
        <p>Frete: R${props.frete.toFixed(2)}</p>
        <p>Total: R${(props.price + props.frete).toFixed(2)}</p>
        <p>Desconto: R${(props.price+ props.frete - discount).toFixed(2)}</p>
        <button className={style.btn}>Pagamento</button>
        <button className={style.btn}>
          <a className={style.link} href="/">
            Continuar comprando
          </a>
        </button>
      </div>
      <div className={style.cupom}>
        <h1>Desconto</h1>
        <input
          className={style.inputTextCupom}
          type="text"
          value={inputValue}
          onChange={handleInputChange}
          placeholder="Código do cupom"
        />
        <button className={style.btn} onClick={() => aplicaDesconto(inputValue)}>
          Aplicar cupom
        </button>
      </div>
    </>
  );
};

export default resumo;
