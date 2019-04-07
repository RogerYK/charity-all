import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import * as serviceWorker from './serviceWorker';
import { BrowserRouter as Router, Switch, Route, Redirect} from 'react-router-dom'

import LoginPage from './pages/Login';
import SignPage from './pages/SignPage';
import SearchPage from './pages/SearchPage';
import DonationPage from './pages/DonationPage';
import DetailPage from './pages/DetailPage';
import ResortPage from './pages/RsortPage';

import UserPage from './pages/UserPage';
import ExplorePage from './pages/ExplorePage';


ReactDOM.render((
    <Router>
        <Switch>
        <Redirect exact from='/' to="/donation" />
        <Route path='/login' component={LoginPage}/>
        <Route path='/sign' component={SignPage} />
        <Route path='/search' component={SearchPage} />
        <Route path='/donation' component={DonationPage} />
        <Route path='/detail/:id' component={DetailPage} />
        <Route path="/resort" component={ResortPage} />
        <Route path="/user" component={UserPage} />
        <Route path="/explore" component={ExplorePage} />
        </Switch>
    </Router>
), document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
