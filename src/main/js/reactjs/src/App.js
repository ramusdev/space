import React from "react";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Navbar from "./Components/Navbar.js";
import TouristList from "./Tourists/TouristList";
import TouristAdd from "./Tourists/TouristAdd";
import TouristEdit from "./Tourists/TouristEdit";
import TouristSingle from "./Tourists/TouristSingle";
import ShipAdd from "./Ship/ShipAdd";
import ShipList from "./Ship/ShipList";
import ShipSingle from "./Ship/ShipSingle";
import ShipEdit from "./Ship/ShipEdit";

function App() {

  return (
      <div className="wrapper-inner">
        <BrowserRouter>
          <Navbar></Navbar>
          <div className = "main">
            <Switch>
              <Route path="/" exact component={TouristList} />
              <Route path="/tourist/add" exact component={TouristAdd} />
              <Route path="/tourist/:id" exact component={TouristSingle} />
              <Route path="/tourist/edit/:id" exact component={TouristEdit} />
              <Route path="/ship/add" exact component={ShipAdd} />
              <Route path="/ship/list" exact component={ShipList} />
              <Route path="/ship/:id" exact component={ShipSingle} />
              <Route path="/ship/edit/:id" exact component={ShipEdit} />
            </Switch>
          </div>
        </BrowserRouter>
      </div>
  );
}

export default App;
