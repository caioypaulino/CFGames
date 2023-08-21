import React, { useState } from "react";
import logoCF from "../../assets/navbar/Logo 2.svg";
import bannerLogin from "../../assets/login/undraw_login_re_4vu2 1.svg";
import styles from './Login.module.css';
import {Link} from 'react-router-dom';

export default function login() {
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
    <div className="login">
      <div className={styles.container}>
        <div className={styles.images}>
          <a href="/"><img className="logoCF" src={logoCF} /></a>
          <img className={styles.img_banner} src={bannerLogin} />
        </div>
        <div className={styles.form}>
          <form onSubmit={handleSubmit}>
            <h1>Faça seu login!</h1>
            <label>
              <p>Email</p>
              <input
                type="text"
                value={email}
                className={styles.input}
                onChange={(e) => setEmail(e.target.value)}
              /><br/>
            </label>
            <label>
              <p>Senha</p>
              <input
                type="password"
                value={password}
                className="password"
                onChange={(e) => setPassword(e.target.value)}
              />
            </label><br/>
            <input className={styles.enviar} type="submit" />
          </form><br/>
          <p>Não possui uma conta? <Link className={styles.link} to="/Cadastro">  registrar-se</Link></p> 
        </div>
      </div>
    </div>
  );
}
