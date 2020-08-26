import React from "react";
import {Link} from "react-router-dom";
import Notification from "../Components/Notification/Notification";

export default class Order extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            ships: [],
            direction: "",
            departure: ""
        };

        this.handleChangeInput = this.handleChangeInput.bind(this);
        this.handleSubmitSearch = this.handleSubmitSearch.bind(this);
        this.notificationComponent = React.createRef();
    }

    render() {
        console.log("Render -->");
        const { ships } = this.state;

        return (
            <div className="tourist-container">
                <div className="container-fluid px-5">
                    <div className="row">
                        <div className="col-12">
                            <div className="title-page">Order</div>
                            <div className="form-finder mb-2">
                                <form className="form-inline" onSubmit={this.handleSubmitSearch}>
                                    <input onChange={this.handleChangeInput} value={this.state.direction} name="direction" type="text"
                                           className="form-control mb-2 mr-sm-2" id="inlineInputDirection" placeholder="Direction" />
                                    <input onChange={this.handleChangeInput} value={this.state.departure} name="departure" type="text"
                                           className="form-control mb-2 mr-sm-2" id="inlineInputDeparture" placeholder="Departure" />
                                    <button type="submit" className="btn btn-outline-primary mb-2">Search</button>
                                </form>
                            </div>
                            <table className="table">
                                <thead className="table__head">
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Direction</th>
                                    <th scope="col">Price</th>
                                    <th scope="col">Seats</th>
                                    <th scope="col">Departure date</th>
                                    <th scope="col">Arrival date</th>
                                    <th scope="col">Actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                {ships.map((item, index) => (
                                    <tr key={item.id}>
                                        <th scope="row">{index + 1}</th>
                                        <td>
                                            <Link to={`/ship/${item.id}`}>
                                                {item.direction}
                                            </Link>
                                        </td>
                                        <td>{item.price}</td>
                                        <td>{item.seats}</td>
                                        <td>{item.departureDate}</td>
                                        <td>{item.arrivalDate}</td>
                                        <td>
                                            <button type="button" className="btn btn-sm btn-outline-success">Order</button>
                                            <Link to={`/ship/${item.id}`}>
                                                <button type="button" className="btn btn-sm btn-outline-primary">Detail</button>
                                            </Link>
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <Notification ref={this.notificationComponent} text={this.state.notificationText} visible={this.state.notificationVisible}></Notification>
            </div>
        )
    }

    componentDidMount() {
        console.log("Did Mount --->");
        this.fetchShips();
    }

    fetchShips() {
        const url = "http://127.0.0.1:8080/api/ship/all";
        fetch(url)
            .then(res => res.json())
            .then(res => {
                this.setState({
                    ships: res
                })
            })
    }

    handleChangeInput(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState( {
            [name]: value
        });
    }

    handleSubmitSearch(event) {
        event.preventDefault();
        console.log("Search --->");

        let url = new URL("http://127.0.0.1:8080/api/ship/search/");
        url.searchParams.set("direction", this.state.direction);
        url.searchParams.set("departure", this.state.departure);

        fetch(url)
            .then(res => res.json())
            .then(res => {
                if(res.length) {
                    this.setState({
                        ships: res
                    });
                }
                console.log(res);
            });
    }
}
