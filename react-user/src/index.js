import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import * as serviceWorker from "./serviceWorker";
import { BrowserRouter as Router } from "react-router-dom";

import { Provider } from "mobx-react";
import userStore from "./store/userStore";
import commonStore from "./store/commonStore";
import uiStore from "./store/uiStore";
import indexStore from "./store/indexStore";
import exploreStore from "./store/exploreStore";
import favoriteStore from "./store/favoriteStore";
import recordStore from "./store/recordStore";
import releaseStore from "./store/releaseStore";
import searchStore from "./store/searchStore";
import detailStore from "./store/detailStore";
import App from "./App";

const stores = {
  userStore,
  commonStore,
  uiStore,
  indexStore,
  exploreStore,
  favoriteStore,
  recordStore,
  releaseStore,
  searchStore,
  detailStore
};

ReactDOM.render(
  <Provider {...stores}>
    <Router>
      <App />
    </Router>
  </Provider>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
