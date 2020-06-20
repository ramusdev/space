import React from 'react'

export default class TouristAdd extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            firstName: '',
            lastName: '',
            gender: '',
            country: '',
            dateOfBirth: '',
            shipKey: '',
            ships: [],
            description: ''
        };

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    render() {
        console.log("Render -->");
        const { ships } = this.state;

        return (
            <div className="tourist-container">
                <form onSubmit={this.handleSubmit}>
                    <div className="container">
                        <div className="row">
                            <div className="col-12">
                                <div className="title-page">Tourists add</div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="firstName" placeholder="First name"
                                        onChange={this.handleChange}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="lastName" placeholder="Last name"
                                        onChange={this.handleChange}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="gender" placeholder="Gender"
                                           onChange={this.handleChange}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="country" placeholder="Country"
                                           onChange={this.handleChange}></input>
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control" name="dateOfBirth" placeholder="Date of birth"
                                           onChange={this.handleChange}></input>
                                </div>
                                <div className="form-group">
                                    <label form="ship">Select the ship</label>
                                    <select className="form-control" id="ship" name="shipKey" onChange={this.handleChange}>
                                        <option value="None">None</option>
                                        {ships.map(ship => (
                                            <option key={ship.id} value={ship.id}>{ship.direction} - {ship.departureDate}</option>
                                        ))}
                                    </select>
                                </div>
                                <div className="form-group">
                                    <label htmlFor="about">Information about tourist</label>
                                    <textarea type="about" className="form-control" id="about" name="description" rows="3" placeholder="Some information about passenger"
                                              onChange={this.handleChange}></textarea>
                                </div>
                                <button className="btn btn-outline-primary" type="submit">Add tourist</button>
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
        const url = "http://127.0.0.1:8080/api/ship/all";
        fetch(url)
            .then(res => res.json())
            .then(res => {
                this.setState({
                    ships: res
                })
            })
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

        const url = "/api/tourist/add";
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
}