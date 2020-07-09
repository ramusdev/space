import React from 'react'
import {forEach} from "react-bootstrap/cjs/ElementChildren";
import Notification from "../Components/Notification/Notification";

export default class ShipEdit extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            id: "",
            direction: "",
            price: "",
            seats: "",
            seatsAvailable: "",
            departureDate: "",
            arrivalDate: "",
            touristsAll: [],
            touristsAdded: []
        };

        this.fetchShip = this.fetchShip.bind(this);
        this.fetchTourists = this.fetchTourists.bind(this);
        this.replaceAddedTourists = this.replaceAddedTourists.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleChangeSeats = this.handleChangeSeats.bind(this);
    }

    render() {
        console.log("Render -->");
        const { id,
            direction,
            price,
            seats,
            seatsAvailable,
            departureDate,
            arrivalDate,
            touristsAll,
            touristsAdded
        } = this.state;

        return (
            <div className="shipadd-container">
                <form onSubmit={this.handleSubmit}>
                    <div className="container">
                        <div className="row">
                            <div className="col-12">
                                <div className="title-page">Ship edit</div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="direction" placeholder="Direction"
                                           onChange={this.handleChange} value={direction}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="price" placeholder="Price"
                                           onChange={this.handleChange} value={price}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="seats" placeholder="Seats"
                                           onChange={this.handleChangeSeats} value={seats}></input>
                                </div>
                                <div className="form-group">
                                    <div className="text-secondary">Available seats: {seatsAvailable}</div>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="departureDate" placeholder="Departure date"
                                           onChange={this.handleChange} value={departureDate}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="arrivalDate" placeholder="Arrival date"
                                           onChange={this.handleChange} value={arrivalDate}></input>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-6">
                                <ul className="list-group">
                                    {touristsAdded.map((tourist, index) => (
                                        <li key={tourist.id} className="list-group-item list-group-item-action d-flex justify-content-between align-items-center">{tourist.firstName + ' ' + tourist.lastName}<button
                                            type="button" onClick={this.handleRemoveTourist.bind(this, index)} className="btn btn-outline-danger btn-sm">Remove</button></li>
                                    ))}
                                </ul>
                            </div>
                            <div className="col-6">
                                <ul className="list-group">
                                    {touristsAll.map((tourist, index) => (
                                        <li key={tourist.id} className="list-group-item list-group-item-action d-flex justify-content-between align-items-center">{tourist.firstName + ' ' + tourist.lastName}<button
                                            type="button" onClick={this.handleAddTourist.bind(this, index)} className="btn btn-outline-primary btn-sm">Add</button></li>
                                    ))}
                                </ul>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-12">
                                <button className="btn btn-outline-primary" type="submit">Edit ship</button>
                            </div>
                        </div>
                    </div>
                </form>
                <Notification text={this.state.notificationText} visible={this.state.notificationVisible}></Notification>
            </div>
        )
    }

    componentDidMount() {
        console.log("Did Mount -->");

        let id = this.props.match.params.id;
        this.fetchShip(id)
            .then(ship => {
                // console.log(ship);
                this.setState({
                    id: ship.id,
                    direction: ship.direction,
                    arrivalDate: ship.arrivalDate,
                    departureDate: ship.departureDate,
                    seats: ship.seats,
                    seatsAvailable: ship.seatsAvailable,
                    price: ship.price,
                    touristsAdded: ship.tourists
                });
            })
            .then(resp => this.fetchTourists())
            .then(resp => this.replaceAddedTourists(resp));
    }

    fetchShip(id) {
        const url = "http://127.0.0.1:8080/api/ship/" + id;
        return fetch(url)
            .then(response => response.json());
    }

    fetchTourists() {
        const url = "http://127.0.0.1:8080/api/tourist/all";
        return fetch(url)
            .then(response => response.json());
            // .then(resp => {
                // this.replaceAddedTourists(resp);
            // });
    }

    replaceAddedTourists(tourists) {
        let { touristsAdded } = this.state;

        let touristsFiltered = tourists.filter(function(element) {
            let isExist = false;
            touristsAdded.forEach(function(elementInner, indexInned) {
                if (elementInner.id == element.id) {
                    isExist = true;
                }
            })
            return !isExist;
        });

        console.log("touristsFiltered: ", touristsFiltered);
        console.log("touristsAdded: ", touristsAdded);
        console.log("touristsAll: ", tourists);

        this.setState({
            touristsAll: touristsFiltered,
        });
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value,
        });
    }

    handleChangeSeats(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState( {
            [name]: value,
            seatsAvailable: value
        });
    }

    handleSubmit(event) {
        console.log("Form submited -->");
        event.preventDefault();
        const id = this.props.match.params.id;
        //const {item} = this.state;
        //const item = this.state;
        //console.log(JSON.stringify(this.state));

        const url = "http://127.0.0.1:8080/api/ship/update/" + id;
        fetch(url, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "PUT",
            dataType: 'json',
            body: JSON.stringify(this.state)
        });
    }

    handleAddTourist(index) {
        console.log("Add tourist -->");

        let touristsAll = Object.assign([], this.state.touristsAll);
        let touristToAdd = touristsAll.splice(index, 1);

        let touristsAdded = Object.assign([], this.state.touristsAdded);
        touristsAdded.push(touristToAdd[0]);

        // Seats available check
        const seatsAvailable = this.state.seatsAvailable;
        const newSeatsAvailable = seatsAvailable - 1;

        if (newSeatsAvailable < 0) {
            this.setState({
                notificationText: 'Error! Not enough available seats!',
                notificationVisible: 'alert alert-danger is-visible'
            });

            setTimeout(() => {
                this.setState({
                    notificationText: 'Error! Not enough available seats!',
                    notificationVisible: 'alert alert-danger is-notvisible'
                });
            }, 5000);

            return false;
        }

        this.setState({
            touristsAdded: touristsAdded,
            touristsAll: touristsAll,
            seatsAvailable: newSeatsAvailable
        });
    }

    handleRemoveTourist(index) {
        console.log("Remove tourist -->")

        let touristsAdded = Object.assign([], this.state.touristsAdded);
        let touristToAdd = touristsAdded.splice(index, 1);

        let touristsAll = Object.assign([], this.state.touristsAll);
        touristsAll.push(touristToAdd[0]);

        const seatsAvailable = this.state.seatsAvailable;
        const newSeatsAvailable = seatsAvailable + 1;

        this.setState({
            touristsAdded: touristsAdded,
            touristsAll: touristsAll,
            seatsAvailable: newSeatsAvailable
        });
    }
}