import React from 'react';
import {Link} from 'react-router-dom';

export default class Navbar extends React.Component {
    render() {
        return(
            <div className="sidenav">
                <div className="sidenav__logo">
                    <div className="sidenav__text">Space <span className="nobold">Travels ®</span></div>
                </div>
                <div className="sidenav__bar">
                    <Link to={`/`}>Tourists list</Link>
                    <Link to={`/tourist/add`}>Tourist add</Link>
                    <Link to={`/ship/list`}>Ship list</Link>
                    <Link to={`/ship/add`}>Ship add</Link>
                    <Link to={`/order`}>Order</Link>
                </div>
            </div>
        )
    }
}

