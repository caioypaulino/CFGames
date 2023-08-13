import React from 'react';
import Navbar from './components/navbar';
import Footer from './components/footer/index';

function Layout({ children }) {
  return (
    <div>
      <Navbar />
      <main>{children}</main>
      <Footer />
    </div>
  );
}

export default Layout;