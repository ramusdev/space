import React from 'react';
import styled from 'styled-components';

//const Container = styled.div`
    //display: $(props => props.display);
    //color: green;
    //transition: top 0.5s ease;
//`;

export default class Notification extends React.Component {
    constructor(props) {
        super(props)

        let classNotificationNotVisible = 'alert alert-danger is-notvisible';
        this.state = {
            classNotification: classNotificationNotVisible
        }

        this.showNotification = this.showNotification.bind(this);
        this.hideNotification = this.hideNotification.bind(this);
    }
    render() {
        /*
        if (this.props.visible) {
            this.showNotification();
        } else {
            this.hideNotification();
        }
        */
        return(
            <div className={this.props.visible} role="alert">
                {this.props.text}
            </div>
        )
    }
    showNotification() {
        let classNotificationVisible = 'alert alert-danger is-visible';
        this.setState({
           classNotification: classNotificationVisible
        });
    }
    hideNotification() {
        let classNotificationNotVisible = 'alert alert-danger is-notvisible';
        this.setState({
            classNotification: classNotificationNotVisible
        });
    }

}