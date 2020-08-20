import React from "react";
import { Link } from "react-router-dom";
import Notification from "../Components/Notification/Notification";

export default class ShipList extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            items: [],
            searchDirection: ""
        };

        this.inputChangeDirection = this.inputChangeDirection.bind(this);
        this.notificationComponent = React.createRef();
    }

    render() {
        console.log("Render -->");

        const { items } = this.state;
        // console.log(items);

        return (
            <div className="tourist-container">
                <div className="container-fluid px-5">
                    <div className="row">
                        <div className="col-12">
                            <div className="title-page">Tourists list</div>
                            <div className="form-finder mb-2">
                                <form className="form-inline">
                                    <input onChange={this.inputChangeDirection} value={this.state.searchDirection} name="searchDirection" type="text"
                                           className="form-control mb-2 mr-sm-2" id="inlineInputDirection" placeholder="Direction" />
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
                                {items.map((item, index) => (
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
                                            <Link to={`/ship/edit/${item.id}`}>
                                                <button type="button" className="btn btn-sm btn-outline-success">Edit</button>
                                            </Link>
                                            <button type="button" className="btn btn-sm btn-outline-danger" onClick={this.deleteItem.bind(this, item.id, index)}>Delete</button>
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
        console.log("Did Mount -->");

        this.fetchData();
    }

    fetchData() {
        const url = "http://127.0.0.1:8080/api/ship/all";
        fetch(url)
            .then(res => {
                return res.json()
            })
            .then(res => {
                console.log(res)
                this.setState({
                    items: res
                })
            })
    }

    deleteItem(userId, index) {
        console.log("Click delete -->");

        const urlRequest = "http://127.0.0.1:8080/api/ship/" + userId;
        fetch(urlRequest, {
            method: 'DELETE',
        })
            .then(res => res.json())
            .then(res => {
                    let itemsUpdated = Object.assign([], this.state.items);
                    itemsUpdated.splice(index, 1);

                    this.setState({
                        items: itemsUpdated
                    });

                    this.notificationComponent.current.showMessage(res.message, res.success);
                }
            )
    }

    inputChangeDirection(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState( {
            [name]: value
        });

        const urlRequest = "http://127.0.0.1:8080/api/ship/search/" + value;

        fetch(urlRequest)
            .then(res => res.json())
            .then(res => {
                if(res.length) {
                    this.setState({
                        items: res
                    });
                }

                console.log(res);
                console.log("----------->");
                console.log(this.state);
            });
    }
}
