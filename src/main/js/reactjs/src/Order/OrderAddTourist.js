import React from "react";
import {Link} from "react-router-dom";
import Notification from "../Components/Notification/Notification";

export default class OrderAddTourist extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            ship: [],
            firstName: '',
            lastName: '',
            gender: '',
            country: '',
            dateOfBirth: '',
            shipIdentifier: '',
            description: ''
        };

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChangeInput = this.handleChangeInput.bind(this);
        this.notificationComponent = React.createRef();
    }

    render() {
        console.log("Render -->");
        const { ship,
            firstName,
            lastName,
            gender,
            country,
            dateOfBirth,
            shipIdentifier,
            description
        } = this.state;

        return (
            <div className="tourist-container">
                <div className="container-fluid px-5">
                    <div className="row">
                        <div className="col-6">
                            <form onSubmit={this.handleSubmit}>
                                <div className="title-page">Tourist</div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="firstName" placeholder="First name"
                                           onChange={this.handleChangeInput} value={firstName}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="lastName" placeholder="Last name"
                                           onChange={this.handleChangeInput} value={lastName}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="gender" placeholder="Gender"
                                           onChange={this.handleChangeInput} value={gender}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="country" placeholder="Country"
                                           onChange={this.handleChangeInput} value={country}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="dateOfBirth" placeholder="Date of birth"
                                           onChange={this.handleChangeInput} value={dateOfBirth}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="ship" placeholder="Date of birth"
                                           onChange={this.handleChangeInput} value={ship.direction} readOnly></input>
                                </div>
                                <div className="form-group">
                                    <label htmlFor="about">Information about tourist</label>
                                    <textarea type="about" className="form-control" id="about" name="description" rows="3" placeholder="Some information about passenger"
                                              onChange={this.handleChangeInput} value={description}></textarea>
                                </div>
                                <button className="btn btn-outline-primary" type="submit">Add tourist</button>
                            </form>
                        </div>
                        <div className="col-6">
                            <div className="title-page">Ship</div>
                            <div className="card">
                                <div className="card-body">
                                    <h4 className="card-title mb-4">{ship.direction}</h4>
                                    <h6 className="card-subtitle mb-3">Id: {ship.id}</h6>
                                    <h6 className="card-subtitle mb-3">Arrival date: {ship.arrivalDate}</h6>
                                    <h6 className="card-subtitle mb-3">Departure date: {ship.departureDate}</h6>
                                    <h6 className="card-subtitle mb-3">Price: {ship.price}</h6>
                                    <h6 className="card-subtitle mb-3">Seats: {ship.seats}</h6>

                                    <Link to={`/ship/${ship.id}`}>
                                        <button type="button" href="#" className="btn btn-outline-success">Details</button>
                                    </Link>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <Notification ref={this.notificationComponent} text={this.state.notificationText} visible={this.state.notificationVisible}></Notification>
            </div>
        )
    }

    componentDidMount() {
        console.log("Did Mount --->");
        this.fetchShip();
    }

    fetchShip() {
        const url = "http://127.0.0.1:8080/api/ship/" + this.props.match.params.id;
        fetch(url)
            .then(res => res.json())
            .then(res => {
                this.setState({
                    ship: res
                })
            })
    }

    handleChangeInput(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value,
        });
    }

    handleSubmit(event) {
        console.log("Form submited -->");
        event.preventDefault();
        // console.log(JSON.stringify(this.state));

        const url = "http://127.0.0.1:8080/api/tourist/add";
        fetch(url, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            redirect: "follow",
            method: "POST",
            dataType: 'json',
            body: JSON.stringify(this.state)
        })
            .then(res => res.json())
            .then(res => {
                // console.log(res);
                // this.fetchShips();
                // this.notificationComponent.current.showMessage(res.message, res.success);
                this.props.history.push(res.redirect);
            });
    }
}
