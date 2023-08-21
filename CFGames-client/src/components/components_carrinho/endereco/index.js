import React, { useState } from "react";
import style from "./Enderecos.module.css";
import { useNavigate } from 'react-router-dom';

const enderecos = () => {
  const enderecosCliente = [
    {
      "endereco": "11111-111, Rua A, Cidade A, AA",
      "frete": 22
    }, 
    {
      "endereco": "22222-222, Rua B, Cidade B, BB",
      "frete": 40
    }  
  ];
  const [enderecoSelecionado, setEnderecoSelecionado] = useState("");
  const handleChangeEndereco = (event) => {
    setEnderecoSelecionado(event.target.value);
  };

  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();
    navigate('/cadEndereco');
  };
  
  return (
    <div className={style.selectAdress}>
      <h1>Selecione o endereço de entrega</h1>
      <form className={style.enderecoList}>
        {enderecosCliente.map((enderecosCliente) => (
          <li key={enderecosCliente.endereco}>
            <input
              type="radio"
              value={enderecosCliente.endereco}
              checked={enderecoSelecionado === enderecosCliente.endereco}
              onChange={handleChangeEndereco}
            />
            {enderecosCliente.endereco}
          </li>
        ))}
      </form>
      <p>Endereço selecionado: {enderecoSelecionado}</p>
      <div className={style.functionsEndereco}>
        <form onSubmit={handleSubmit}>
          <input type="submit" value={"Novo endereço"} className={style.btnNewAddress}/>
        </form>
        <form>
          <input type="submit" value={"Calcular frete"} className={style.btnCalcFrete}/>
        </form>
      </div>
    </div>
  );
}

export default enderecos;
