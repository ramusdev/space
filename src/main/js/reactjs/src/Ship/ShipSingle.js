import React from 'react';

export default class ShipSingle extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            data: []
        };

        this.fetchData = this.fetchData.bind(this);
    }

    render() {
        console.log("Render -->");
        const {data} = this.state;
        console.log(data)

        return (
            <div className="tourist-container">
                <div className="container">
                    <div className="row">
                        <div className="col-12">
                            <div className="title-page">Ship single</div>
                            <div className="card">
                                <div className="card-body">
                                    <h4 className="card-title mb-4">To {data.direction}</h4>
                                    <h6 className="card-subtitle mb-3">Id: {data.id}</h6>
                                    <h6 className="card-subtitle mb-3">Arrival date: {data.arrivalDate}</h6>
                                    <h6 className="card-subtitle mb-3">Departure date: {data.departureDate}</h6>
                                    <h6 className="card-subtitle mb-3">Price: {data.price}</h6>
                                    <h6 className="card-subtitle mb-3">Seats: {data.seats}</h6>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    componentDidMount() {
        console.log("Did Mount -->");
        this.fetchData(this.props.match.params.id);
    }

    componentDidUpdate(prevProps) {
        if (prevProps.match.params.id !== this.props.match.params.id) {
            console.log("Did Update  -->");
            this.fetchData(this.props.match.params.id);
        }
    }

    fetchData(id) {
        const singleShipUrl = "http://127.0.0.1:8080/api/ship/" + id;
        fetch(singleShipUrl)
            .then(res => res.json())
            .then(result => {
                this.setState({
                    data: result
                })
            })
    }

}