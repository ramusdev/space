import React from 'react';
import './Notification.css';
// import styled from 'styled-components';

export default class Notification extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            notificationClass: "alert alert-success is-notvisible",
            notificationText: ""
        };

        this.showMessage = this.showMessage.bind(this);
    }
    render() {
        return(
            <div className={this.state.notificationClass} role="alert">
                {this.state.notificationText}
            </div>
        )
    }

    showMessage(message, type) {

        let notificationVisibleClass = type ? "alert alert-success is-visible" : "alert alert-danger is-visible";
        let notificationNotVisibleClass = type ? "alert alert-error is-notvisible" : "alert alert-danger is-notvisible";

        this.setState({
            notificationClass: notificationVisibleClass,
            notificationText: message,
        })

        setTimeout(() => {
            this.setState({
                notificationClass: notificationNotVisibleClass
            })
        }, 4000);
    }

    componentDidMount() {
        console.log("Did mount -------->");
    }
}