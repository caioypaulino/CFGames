import usersList from "./clientes.json"
import productsList from "./products.json";
import cupomList from "./cupons.json"

// storage Carrinho
export const salvarNoCarrinho = (produto) => {
  const carrinho = JSON.parse(localStorage.getItem("carrinho")) || [];
  carrinho.push(produto);
  localStorage.setItem("carrinho", JSON.stringify(carrinho));
};

export const removerDoCarrinho = (produtoId) => {
  const carrinho = JSON.parse(localStorage.getItem("carrinho")) || [];
  const novoCarrinho = carrinho.filter((item) => item.id !== produtoId);
  localStorage.setItem("carrinho", JSON.stringify(novoCarrinho));
};

export const getCarrinho = () => {
  return JSON.parse(localStorage.getItem("carrinho")) || [];
};

// storage produtos
export const salvarNoProduto = () => {
    localStorage.setItem("products", JSON.stringify(productsList));
  };
export const getProducts = () => {
  return JSON.parse(localStorage.getItem("products")) || [];
};

// storage cupons
export const salvarNoCupons = () => {
  localStorage.setItem("cupons", JSON.stringify(cupomList));
};
export const getCupom = () => {
  return JSON.parse(localStorage.getItem("cupons")) || [];
};

// storage users
export const getCliente = () => {
  return JSON.parse(localStorage.getItem("users")) || [];
};

export const salvarCliente = (cliente) => {
  const clientes = JSON.parse(localStorage.getItem("users")) || [];
  clientes.push(cliente);
  localStorage.setItem("users", JSON.stringify(clientes));
};

export const removerCliente = (id) => {
  const cliente = JSON.parse(localStorage.getItem("users")) || [];
  const novoCliente = cliente.filter((item) => item.id !== id);
  localStorage.setItem("users", JSON.stringify(novoCliente));
};

export const getClienteById = (id) => {
  const clientes = getCliente();
  const clienteEncontrado = clientes.find((cliente) => cliente.id === id);
  return clienteEncontrado || null;
};
