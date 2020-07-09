import React from 'react';
import './Notification.css';
// import styled from 'styled-components';

export default class Notification extends React.Component {
    constructor(props) {
        super(props)

        // const {text, visible} = props;

        this.state = {
            notificationText: this.props.text,
            notificationVisible: this.props.visible
        };

        console.log(props);

        // let classNotificationNotVisible = 'alert alert-danger is-notvisible';
        // this.state = {
            // classNotification: classNotificationNotVisible
        // }
        /*
        setTimeout(() => {
            this.setState({
                notificationText: 'Error! Not enough available seats!',
                notificationVisible: 0
            });
        }, 2000);
        */
    }
    render() {
        // const {text, visible} = this.props;
        // console.log(this.state);

        const mod = this.state.notificationVisible ? "is-visible" : "is-notvisible";
        const notificationClass = "alert alert-danger " + mod;

        return(
            <div className={notificationClass} role="alert">
                {this.state.notificationText}
            </div>
        )
    }

    /*
    componentDidMount() {
        setTimeout(() => {
            this.setState({
                notificationText: 'Error! Not enough available seats!',
                notificationVisible: 0
            });
        }, 5000);
    }
    */
}