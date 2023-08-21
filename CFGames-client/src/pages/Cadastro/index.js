import React, { useState } from "react";
import logoCF from "../../assets/navbar/Logo 2.svg";
import bannerCadastro from "../../assets/cadastro/undraw_old_day_-6-x25 1.svg";
import styles from "./Cadastro.module.css";
import {Link} from 'react-router-dom';

export default function cadastro() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        const data = await response.json();
        // Faça algo com os dados de resposta, como armazená-los no estado da sua aplicação
        alert(response.json);
      } else {
        // Lidar com um erro de autenticação
        alert(response.json);
      }
    } catch (error) {
      console.error(error);
      alert(error);
      // Lidar com um erro de rede
    }
  };

  return (
    <div className="cadastro">
      <div className={styles.container}>
        <div className={styles.form}>
          <a href="/"><img className="logoCF" src={logoCF} /></a>
          <form onSubmit={handleSubmit}>
            <h1>Criar conta</h1>
            <label>
              <p>Nome completo</p>
              <input
                className={styles.input}
                type="text"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </label><br/>
            <label className="datecpf">
              <p>CPF/CNPJ</p>
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <p>Data de nascimento</p>
              <input
                className={styles.date}
                type="date"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </label><br/>
            <label>
              <p>Email</p>
              <input
                className={styles.input}
                type="text"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              /><br/>
            </label>
            <label>
              <p>Senha</p>
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </label><br/>
            <input className={styles.enviar} type="submit" />
          </form><br/>
          <p>Já possui uma conta? <Link className={styles.link} to="/Login">Logar</Link></p>
        </div>
        <div className="image-banner">
          <img className={styles.img_banner} src={bannerCadastro} />
        </div>
      </div>
    </div>
  );
}
