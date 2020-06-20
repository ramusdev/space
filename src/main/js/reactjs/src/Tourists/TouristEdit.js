import React from 'react'

export default class TouristEdit extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            firstName: '',
            lastName: '',
            gender: '',
            country: '',
            dateOfBirth: '',
            description: ''
        };

        this.fetchData = this.fetchData.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    render() {
        console.log("Render -->");
        //const {data} = this.state;

        return (
            <div className="tourist-add">
                <form onSubmit={this.handleSubmit}>
                    <div className="title-page">Tourists edit id: {this.props.match.params.id}</div>
                    <div className="form-group">
                        <input type="text" className="form-control" name="firstName" placeholder="First name"
                               onChange={this.handleChange} value={this.state.firstName}></input>
                    </div>
                    <div className="form-group">
                        <input type="text" className="form-control" name="lastName" placeholder="Last name"
                               onChange={this.handleChange} value={this.state.lastName}></input>
                    </div>
                    <div className="form-group">
                        <input type="text" className="form-control" name="gender" placeholder="Gender"
                               onChange={this.handleChange} value={this.state.gender}></input>
                    </div>
                    <div className="form-group">
                        <input type="text" className="form-control" name="country" placeholder="Country"
                               onChange={this.handleChange} value={this.state.country}></input>
                    </div>
                    <div className="form-group">
                        <input type="text" className="form-control" name="dateOfBirth" placeholder="Date of birth"
                               onChange={this.handleChange} value={this.state.dateOfBirth}></input>
                    </div>
                    <div className="form-group">
                        <label htmlFor="about">Information about tourist</label>
                        <textarea type="about" className="form-control" id="about" name="description" rows="3" placeholder="Some information about passenger"
                                  onChange={this.handleChange} value={this.state.description}></textarea>
                    </div>
                    <button className="btn btn-outline-primary" type="submit">Add tourist</button>
                </form>
            </div>
        )
    }

    componentDidMount() {
        console.log("Did Mount -->");
        this.fetchData(this.props.match.params.id);
    }

    fetchData(id) {
        const url = "http://127.0.0.1:8080/api/employees/" + id;
        fetch(url)
            .then(res => res.json())
            .then(result => {
                this.setState({
                    firstName: result.firstName,
                    lastName: result.lastName,
                    gender: result.gender,
                    country: result.country,
                    dateOfBirth: result.dateOfBirth,
                    description: result.description
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

        //let item = {...this.state.item};
        //item[name] = value;
        //this.setState({item});
    }

    handleSubmit(event) {
        console.log("Form submited -->");

        event.preventDefault();
        //const {item} = this.state;
        //const item = this.state;
        console.log(JSON.stringify(this.state));

        const url = "/api/employees/update/" + this.props.match.params.id;
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
}