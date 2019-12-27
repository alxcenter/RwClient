import React from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import IconButton from '@material-ui/core/IconButton';
import SwapHorizOutlinedIcon from '@material-ui/icons/SwapHorizOutlined';
import Container from '@material-ui/core/Container';
import Asynchronous from './Autocomplete';
import MaterialUIPickers from './DateUI';
import ContainedButtons from './SearchButton';
import Grid from '@material-ui/core/Grid';
import TrainListTable from './TrainTable';
import CaptchaPopup from './CaptchaDialog';
import {getDataAction} from './TrainSearcher.js'
import AddUsersDialog from "./AddUsersDialog";

export default function FixedContainer() {
    const [captchaPopup, setCaptchaPopupOpen] = React.useState(false);
    const [trainList, setTrainListOpen] = React.useState(null);
    const [openPassengerOptions, setOpenPassengerOptions] = React.useState(false);
    const [monitor, setMonitor] = React.useState({fromStation: null, toStation: null, date: null});

    /*вызываем при нажатии  на кнопку поиска поездов*/
    let handleSearchButton = function () {
        if (!monitor.fromStation) {
            alert("Введите станцию отправления");
        } else if (!monitor.toStation) {
            alert("Введите станцию прибытия");
        } else {
            getDataAction(monitor)
                .catch(() => setCaptchaPopupOpen(true))
                .then(setTrainListOpen);
        }
    };

    let setPassengers = function(passengers){
        let temp = monitor;

    };

    let from = (<Asynchronous autocompleteName="Станция отправления"
                              id={"async_from"}
                              setStation={(station) => {
                                  monitor.fromStation = station;
                              }}

    />);

    let to = (<Asynchronous autocompleteName="Станция прибытия"
                            id={"async_to"}
                            setStation={(station) => {
                                monitor.toStation = station;
                            }}
    />);


    /*Высызываем при нажатии кномки смены направления*/


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
                            action={handleSearchButton}/>
                    </Grid>
                </Grid>
                <TrainListTable trains={trainList}
                                setTrain={(train) => {
                                    monitor.trainNumber = train;
                                }}
                                setPassengers={(passengers) => {
                                    let temp = monitor;
                                    temp.passengers = passengers;
                                    setMonitor(temp);
                                }}
                                setPlaceFilter={(placeFilter) => {
                                    let temp = monitor;
                                    temp.placeFilter = placeFilter;
                                    setMonitor(temp);
                                }}
                />
                <CaptchaPopup state={captchaPopup}
                              close={() => setCaptchaPopupOpen(false)}
                              monitor={monitor}
                              renderTrainList={setTrainListOpen}/>
                <AddUsersDialog
                    open={openPassengerOptions}
                    action={setOpenPassengerOptions}
                    close={() => setOpen(false)}
                    setPassengers={props.setPassengers}
                    setPlaceFilter={props.setPlaceFilter}
                    onCreate = {createMonitoring}
                />
            </Container>
        </React.Fragment>
    );
}