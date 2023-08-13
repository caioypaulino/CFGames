import logo from "../../assets/navbar/Logo 2.svg";
import accountIcon from "../../assets/navbar/_2350081091120.svg";
import buyIcon from "../../assets/navbar/Camada_x0020_1 (1).svg";
import styles from "./Navbar.module.css";
import React from "react";

function navbar() {
  return (
    <header>
      <div className={styles.navbar}>
        <ul>
          <li>
            <a href="/">
              <img className="logoCF" src={logo} />
            </a>
          </li>
          <li>
            <input type={Text} className={styles.inputText} />
          </li>
          <li>
            <a href="/login">
              <img src={accountIcon} />
            </a>
          </li>
          <li>
            <a href="/carrinho">
              <img src={buyIcon} />
            </a>
          </li>
        </ul>
      </div>
    </header>
  );
}

export default navbar;
