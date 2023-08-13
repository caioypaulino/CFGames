import React, { useState } from "react";
import "./enderecos.css";
import { useNavigate } from 'react-router-dom';

const enderecos = () => {
  const enderecosCliente = [
    "11111-111, Rua A, Cidade A, AA",
    "22222-222, Rua B, Cidade B, BB",
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
    <div className="selectAdress">
      <h1>Selecione o endereço de entrega</h1>
      <form className="enderecoList">
        {enderecosCliente.map((enderecosCliente) => (
          <li key={enderecosCliente}>
            <input
              type="radio"
              value={enderecosCliente}
              checked={enderecoSelecionado === enderecosCliente}
              onChange={handleChangeEndereco}
            />
            {enderecosCliente}
          </li>
        ))}
      </form>
      <p>Endereço selecionado: {enderecoSelecionado}</p>
      <div className="functionsEndereco">
        <form onSubmit={handleSubmit}>
          <input type="submit" value={"Novo endereço"} className="btnNewAddress"/>
        </form>
        <form>
          <input type="submit" value={"Calcular frete"} className="btnCalcFrete"/>
        </form>
      </div>
    </div>
  );
}

export default enderecos;
