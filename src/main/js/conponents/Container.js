import React from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import Container from '@material-ui/core/Container';
import Asynchronous from './Autocomplete';
import MaterialUIPickers from './DateUI';
import ContainedButtons from './SearchButton';
import CaptchaPopup from './CaptchaDialog';
import {resolveCaptcha, getDataAction} from './TrainSearcher.js'

export default function FixedContainer() {

    const [open, setOpen] = React.useState(false);
    const [monitor, setMonitor] = React.useState({fromStation: null, toStation: null, date: null});



    return (
        <React.Fragment>
            <CssBaseline/><br/>
            <Container fixed>
                <div><Asynchronous autocompleteName="Станция отправления"
                                   id={"async_from"}
                                   setStation={(station) => {
                                       console.log("SET STATION " + station);
                                       monitor.fromStation = station;
                                   }}/>
                    <Asynchronous autocompleteName="Станция прибытия"
                                  id={"async_to"}
                                  setStation={(station) => {
                                      monitor.toStation = station;
                                  }}/></div>
                <MaterialUIPickers setDate={(date) => {
                    let dateString = date.toISOString().split("T")[0];
                    monitor.date = dateString;
                }}/>
                <ContainedButtons acti={() => {
                    getDataAction(monitor)
                        .catch(() => setOpen(true))
                        .then((value) => console.log('sucess ' + value));
                }}/>
                <CaptchaPopup state={open} close={() => setOpen(false)} monitor={monitor}/>
            </Container>
        </React.Fragment>
    );
}