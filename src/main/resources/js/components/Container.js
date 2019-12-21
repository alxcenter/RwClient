import React from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import IconButton from '@material-ui/core/IconButton';
import SwapHorizOutlinedIcon from '@material-ui/icons/SwapHorizOutlined';
import Container from '@material-ui/core/Container';
import Asynchronous from './Autocomplete';
import MaterialUIPickers from './DateUI';
import ContainedButtons from './SearchButton';
import Grid from '@material-ui/core/Grid';
import Table from './Table';
import CaptchaPopup from './CaptchaDialog';
import {getDataAction} from './TrainSearcher.js'

export default function FixedContainer() {

    const [captchaPopup, setCaptchaPopupOpen] = React.useState(false);
    const [trainList, setTrainListOpen] = React.useState(null);
    const [monitor, setMonitor] = React.useState({fromStation: null, toStation: null, date: null});пше

    let from = (<Asynchronous autocompleteName="Станция отправления"
                             id={"async_from"}
                             setStation={(station) => {
                                 monitor.fromStation = station;
                             }}
    />);

    let to =(<Asynchronous autocompleteName="Станция прибытия"
                           id={"async_to"}
                           setStation={(station) => {
                               monitor.toStation = station;
                           }}
    />);

    return (
        <React.Fragment>
            <CssBaseline/><br/>
            <Container fixed>
                <Grid container direction="row" justify="center" alignItems="center">
                    <Grid item>
                        {from}
                    </Grid>
                    <Grid item>
                        <IconButton color="secondary" aria-label="Change directions" disableFocusRipple={true}
                                    disableRipple={true}>
                            <SwapHorizOutlinedIcon color={"secondary"} fontSize={"large"}/>
                        </IconButton>
                    </Grid>
                    <Grid item>
                        {to}
                    </Grid>
                </Grid>
                <Grid container direction="column" justify="center" alignItems="center">
                    <Grid item>
                        <MaterialUIPickers setDate={(date) => {
                            let dateString = date.toISOString().split("T")[0];
                            monitor.date = dateString;
                        }}/>
                    </Grid>
                    <Grid item>
                        <ContainedButtons
                            action={() => {
                                if (monitor.fromStation == null || monitor.toStation == null) {

                                } else {
                                    getDataAction(monitor)
                                        .catch(() => setCaptchaPopupOpen(true))
                                        .then(setTrainListOpen);
                                }
                            }}/>
                    </Grid>
                </Grid>
                <Table trains={trainList}/>
                <CaptchaPopup state={captchaPopup}
                              close={() => setCaptchaPopupOpen(false)}
                              monitor={monitor}
                              renderTrainList={setTrainListOpen}/>
            </Container>
        </React.Fragment>
    );
}