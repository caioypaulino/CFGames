import banner from "../../assets/home/Banner.svg";
import React from "react";
import styles from "./Home.module.css";
import Product from "../../components/products/produto";

const Home = () => {
  return (
    <div className={styles.home}>
      <img className={styles.banner} src={banner} alt="Banner" />
      <h1 className={styles.titleNewProducts}>Novos produtos!</h1>
      <div className={styles.listProducts}>
        {jogos.map((jogo, index) => (
          <div key={index}>
            <Product
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

const jogos = [
  {
    id: 1,
    svgString: '<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100"><circle cx="50" cy="50" r="40" fill="red" /></svg>',
    nome: "The Witcher 3: Wild Hunt",
    preco: 59.99,
    desenvolvedora: "CD Projekt Red",
  },
  {
    id: 2,
    svgString: '<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100"><circle cx="50" cy="50" r="40" fill="red" /></svg>',
    nome: "Red Dead Redemption 2",
    preco: 49.99,
    desenvolvedora: "Rockstar Games",
  },
  {
    id: 3,
    svgString: '<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100"><circle cx="50" cy="50" r="40" fill="red" /></svg>',
    nome: "Assassin's Creed Valhalla",
    preco: 69.99,
    desenvolvedora: "Ubisoft",
  },
  {
    id: 4,
    svgString: '<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100"><circle cx="50" cy="50" r="40" fill="red" /></svg>',
    nome: "Cyberpunk 2077",
    preco: 39.99,
    desenvolvedora: "CD Projekt Red",
  },
  {
    id: 5,
    svgString: '<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100"><circle cx="50" cy="50" r="40" fill="red" /></svg>',
    nome: "The Last of Us Part II",
    preco: 59.99,
    desenvolvedora: "Naughty Dog",
  },
  {
    id: 6,
    svgString: '<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100"><circle cx="50" cy="50" r="40" fill="red" /></svg>',
    nome: "Call of Duty: Modern Warfare",
    preco: 49.99,
    desenvolvedora: "Infinity Ward",
  },
  {
    id: 7,
    svgString: '<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100"><circle cx="50" cy="50" r="40" fill="red" /></svg>',
    nome: "God of War",
    preco: 29.99,
    desenvolvedora: "Santa Monica Studio",
  },
  {
    id: 8,
    svgString: '<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100"><circle cx="50" cy="50" r="40" fill="red" /></svg>',
    nome: "FIFA 22",
    preco: 59.99,
    desenvolvedora: "EA Sports",
  },
  {
    id: 9,
    svgString: '<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100"><circle cx="50" cy="50" r="40" fill="red" /></svg>',
    nome: "Minecraft",
    preco: 19.99,
    desenvolvedora: "Mojang Studios",
  },
  {
    id: 10,
    svgString: '<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100"><circle cx="50" cy="50" r="40" fill="red" /></svg>',
    nome: "Horizon Zero Dawn",
    preco: 39.99,
    desenvolvedora: "Guerrilla Games",
  },
  {
    id: 11,
    svgString: '<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100"><circle cx="50" cy="50" r="40" fill="red" /></svg>',
    nome: "Elden Ring",
    preco: 49.99,
    desenvolvedora: "FromSoftware",
  },
];
