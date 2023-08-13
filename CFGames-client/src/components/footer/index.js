import React from "react";
import styles from "./Footer.module.css"; // Arquivo CSS para estilização
import LogoCFGames from "../../assets/footer/Logo Branca 2.svg";

function Footer() {
  return (
    <footer className={styles.footer}>
      <div className={styles.footerColumn}>
        <a href="/">
          <img src={LogoCFGames} alt="Logo" className={styles.logo} />
        </a>
      </div>
      <div className={styles.footerColumn}>
        <h4>Contato</h4>
        <ul>
          <li>Email: CFGames@gmail.com</li>
          <li>Telefone: (123) 456-7890</li>
          <li>Endereço: Rua Exemplo, 123</li>
        </ul>
      </div>
      <div className={styles.footerColumn}>
        <h4>Redes Sociais</h4>
        <ul className={styles.socialmedia}>
          <li>
            <a href="#">Facebook</a>
          </li>
          <li>
            <a href="#">Twitter</a>
          </li>
          <li>
            <a href="#">Instagram</a>
          </li>
        </ul>
      </div>
    </footer>
  );
}

export default Footer;
