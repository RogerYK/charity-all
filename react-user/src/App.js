import React, { Component } from "react";
import { Switch, Route } from "react-router-dom";

import "./global.scss";
import styles from './app.module.scss'

import Header from "./components/Header";
import Footer from "./components/Footer";
import DonationPage from "./pages/DonationPage";
import ExplorePage from "./pages/ExplorePage";
import LoginPage from "./pages/Login";
import SignPage from "./pages/SignPage";
import UserPage from "./pages/UserPage";
import SearchPage from "./pages/SearchPage";
import DetailPage from "./pages/DetailPage";
import NewsDetail from "./pages/NewsDetail";
import UserDetail from "./pages/UserDetail";
import ResortPage from "./pages/RsortPage";
import AddProjectPage from './pages/AddProjectPage'

class App extends Component {
  render() {
    return (
      <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
        <Header />
        <div className={styles.app}>
          <Switch>
            <Route exact path="/" component={DonationPage} />
            <Route path="/explore" component={ExplorePage} />
            <Route path="/login" component={LoginPage} />
            <Route path="/sign" component={SignPage} />
            <Route path="/user/detail/:id" component={UserDetail} />
            <Route path="/user" component={UserPage} />
            <Route path="/search" component={SearchPage} />
            <Route path="/detail/:id" component={DetailPage} />
            <Route path="/news/detail/:id" component={NewsDetail} />
            <Route path="/resort" component={ResortPage} />
            <Route path="/addProject" component={AddProjectPage} />
          </Switch>
          <Footer />
        </div>
      </div>
    );
  }
}

export default App;
