import React from 'react';
import Navbar from './components/navbar';
import Footer from './components/footer/index';
import styles from './Layout.module.css'

function Layout({ children }) {
  return (
    <div className={styles.container}>
      <Navbar />
      <main className={styles.mainContainer}>{children}</main>
      <Footer />
    </div>
  );
}

export default Layout;