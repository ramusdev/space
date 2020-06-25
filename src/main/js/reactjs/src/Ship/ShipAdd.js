import React from 'react'

export default class ShipAdd extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            direction: '',
            price: '',
            seats: '',
            departureDate: '',
            arrivalDate: '',
            touristsAll: [],
            touristsAdded: []
        };

        this.fetchData = this.fetchData.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    render() {
        console.log("Render -->");
        const { touristsAll, touristsAdded } = this.state;

        return (
            <div className="shipadd-container">
                <form onSubmit={this.handleSubmit}>
                    <div className="container">
                        <div className="row">
                            <div className="col-12">
                                <div className="title-page">Ship add</div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="direction" placeholder="Direction"
                                           onChange={this.handleChange} value={this.state.direction}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="price" placeholder="Price"
                                           onChange={this.handleChange} value={this.state.price}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="seats" placeholder="Seats"
                                           onChange={this.handleChange} value={this.state.seats}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="departureDate" placeholder="Departure date"
                                           onChange={this.handleChange} value={this.state.departureDate}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="arrivalDate" placeholder="Arrival date"
                                           onChange={this.handleChange} value={this.state.arrivalDate}></input>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-6">
                                <ul className="list-group">
                                    {touristsAdded.map((tourist, index) => (
                                        <li key={tourist.id} className="list-group-item list-group-item-action d-flex justify-content-between align-items-center">{tourist.firstName + ' ' + tourist.lastName}<button
                                            onClick={this.handleRemoveTourist.bind(this, index)} className="btn btn-outline-danger btn-sm">Remove</button></li>
                                    ))}
                                </ul>
                            </div>
                            <div className="col-6">
                                <ul className="list-group">
                                    {touristsAll.map((tourist, index) => (
                                        <li key={tourist.id} className="list-group-item list-group-item-action d-flex justify-content-between align-items-center">{tourist.firstName + ' ' + tourist.lastName}<button
                                            onClick={this.handleAddTourist.bind(this, index)} className="btn btn-outline-primary btn-sm">Add</button></li>
                                    ))}
                                </ul>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-12">
                                <button className="btn btn-outline-primary" type="submit">Add ship</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

        )
    }

    componentDidMount() {
        console.log("Did Mount -->");

        this.fetchData();
    }

    fetchData() {
        const url = "http://127.0.0.1:8080/api/tourist/all";
        fetch(url)
            .then(res => res.json())
            .then(result => {
                this.setState({
                    touristsAll: result
                })
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

    handleSubmit(event) {
        console.log("Form submited -->");

        event.preventDefault();
        console.log(JSON.stringify(this.state));

        const url = "http://127.0.0.1:8080/api/ship/add";
        fetch(url, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
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

        this.setState({
            touristsAdded: touristsAdded,
            touristsAll: touristsAll
        });
    }

    handleRemoveTourist(index) {
        console.log("Remove tourist -->")

        let touristsAdded = Object.assign([], this.state.touristsAdded);
        let touristToAdd = touristsAdded.splice(index, 1);

        let touristsAll = Object.assign([], this.state.touristsAll);
        touristsAll.push(touristToAdd[0]);

        this.setState({
            touristsAdded: touristsAdded,
            touristsAll: touristsAll
        });
    }
}