import React from 'react';

export default class TouristSingle extends React.Component {
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

        return (
            <div className="tourist-container">
                <div className="container-fluid px-5">
                    <div className="row">
                        <div className="col-12">
                            <div className="title-page">Tourist single</div>
                            <div className="card">
                                <div className="card-body">
                                    <h4 className="card-title mb-4">{data.firstName + ' ' + data.lastName}</h4>
                                    <h6 className="card-subtitle mb-3">Id: {data.id}</h6>
                                    <h6 className="card-subtitle mb-3">Gender: {data.gender}</h6>
                                    <h6 className="card-subtitle mb-3">Date of birth: {data.dateOfBirth}</h6>
                                    <h6 className="card-subtitle mb-3">Country: {data.country}</h6>
                                    <p>{data.description}</p>
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
        const singleTouristUrl = "http://127.0.0.1:8080/api/tourist/" + id;
        fetch(singleTouristUrl)
            .then(res => res.json())
            .then(result => {
                this.setState({
                    data: result
                })
            });
    }

}