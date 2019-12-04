'use strict';

import ButtonAppBar from './components/navbar.js';
import TelegramLogin from './components/telegramAuth.js';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./components/client');
// end::vars[]

// tag::app[]
class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {monitorings: []};
    }

    componentDidMount() {
        client({method: 'GET', path: '/monitorings'}).done(response => {
            this.setState({monitorings: response.entity});
        });
    }

    render() {
        return (
            <div>
                <ButtonAppBar/>
                <TelegramLogin/>
                <MonitoringList monitorings={this.state.monitorings}/>
            </div>

        )
    }
}
// end::app[]

// tag::employee-list[]
class MonitoringList extends React.Component{
    render() {
        const monitorings = this.props.monitorings.map(monitoring =>
            <Monitoring key={monitoring.id} monitoring={monitoring}/>
        );
        return (
            <table>
                <thead>
                <tr>
                    <th>From</th>
                    <th>To</th>
                    <th>Date</th>
                    <th>TrainNumber</th>
                </tr>
                </thead>
                <tbody>
                    {monitorings}
                </tbody>
            </table>
        )
    }
}
// end::employee-list[]

// tag::employee[]
class Monitoring extends React.Component{
    render() {
        return (
            <tr>
                <td>{this.props.monitoring.fromStation}</td>
                <td>{this.props.monitoring.toStation}</td>
                <td>{this.props.monitoring.date}</td>
                <td>{this.props.monitoring.trainNumber}</td>
            </tr>
        )
    }
}
// end::employee[]

// tag::render[]
ReactDOM.render(
    <App />,
    document.getElementById('react')
)
// end::render[]
