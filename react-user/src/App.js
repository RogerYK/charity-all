import React, { Component } from 'react';
import {Switch, Route} from 'react-router-dom'

import './global.scss'

import Header from './components/Header';
import Footer from './components/Footer';
import DonationPage from './pages/DonationPage'
import ExplorePage from './pages/ExplorePage'
import LoginPage from './pages/Login';
import SignPage from './pages/SignPage';
import UserPage from './pages/UserPage';
import SearchPage from './pages/SearchPage';
import DetailPage from './pages/DetailPage';

class App extends Component {
  render() {
    return (
      <div>
        <Header />
          <Switch>
            <Route exact path="/" component={DonationPage}/>
            <Route path="/explore" component={ExplorePage} />
            <Route path="/login" component={LoginPage} />
            <Route path="/sign" component={SignPage} />
            <Route path="/user" component={UserPage} />
            <Route path="/search" component={SearchPage} />
            <Route path="/detail/:id" component={DetailPage} />
          </Switch>
        <Footer />
      </div>
    );
  }
}

export default App;
