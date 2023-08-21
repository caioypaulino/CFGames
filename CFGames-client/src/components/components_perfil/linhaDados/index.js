import React from "react";
import styles from "./linhaDados.module.css"
import icon from "../../../assets/buttons/Frame (6).svg"

const linhaDados = (props) => {

    const dadoExibicao = props.tipo === 'senha' || props.tipo === 'id' ? '**********' : props.dado;

    return (
        <div className={styles.container}>
            <p className={styles.type}>{props.tipo}:</p>
            <p className={styles.data}>{dadoExibicao}</p>
            <button className={styles.btnIcon}><img className={styles.iconEdite} src={icon}/></button>   
        </div>
    )
}

export default linhaDados