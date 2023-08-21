import React from "react";
import styles from "./tabelaActions.module.css";

const tabelaActions = (props) => {
  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <h1 className={styles.titleContainer}>Dados da conta</h1>
      </div>
      <div className={styles.containerButtons}>
        <button>Meus dados</button>
        <button>Endereços</button>
        <button>Cartões de crédito</button>
        <button>Meus pedidos</button>
      </div>
    </div>
  );
};

export default tabelaActions;
