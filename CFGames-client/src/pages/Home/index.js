import React, { useState, useEffect } from 'react';
import banner from "../../assets/home/Banner.svg";
import styles from "./Home.module.css";
import Product from "../../components/products/index";
import productsData from "../../utils/products.json"

const Home = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    setProducts(productsData);
  }, []);

  return (
    <div className={styles.home}>
      <img className={styles.banner} src={banner} alt="Banner" />
      <h1 className={styles.titleNewProducts}>Novos produtos!</h1>
      <div className={styles.listProducts}>
        {products.map((jogo, index) => (
          <div key={index}>
            <Product
              key={jogo.id}
              id={jogo.id}
              image={jogo.svgString}
              nome={jogo.nome}
              preco={jogo.preco}
              desenvolvedora={jogo.desenvolvedora}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Home;
