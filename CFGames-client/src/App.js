import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home/index.js";
import React from "react";
import Login from "./pages/Login/index.js";
import Cadastro from "./pages/Cadastro/index.js";
import Carrinho from "./pages/carrinho";
import Layout from "./Layout";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout><Home /></Layout>} />
        <Route path="/carrinho" element={<Layout><Carrinho /></Layout>} />
        <Route path="/login" element={<Login />} />
        <Route path="/cadastro" element={<Cadastro />} />
      </Routes>
    </BrowserRouter>
  );
}
