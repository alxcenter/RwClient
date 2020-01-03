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
import {getTrainList, createMonitoring} from './TrainSearcher'
import AddUsersDialog from "./AddUsersDialog";
import LinearProgress from "@material-ui/core/LinearProgress";
import Fade from "@material-ui/core/Fade";
// import {getEmptyTrains} from "./TrainSceleton";


export default function FixedContainer() {
    const [captchaPopup, setCaptchaPopupOpen] = React.useState(false);
    const [trainList, setTrainListOpen] = React.useState(null);
    const [openPassengerOptions, setOpenPassengerOptions] = React.useState(false);
    const [monitor, setMonitor] = React.useState({fromStation: null, toStation: null, date: null});
    const [loading, setLoading] = React.useState(false);


    /*вызываем при нажатии на кнопку поиска поездов*/
    let handleSearchButton = function () {
        if (!monitor.fromStation) {
            alert("Введите станцию отправления");
        } else if (!monitor.toStation) {
            alert("Введите станцию прибытия");
        } else {
            setLoading(true);

            getTrainList(monitor)
                .then(setTrainListOpen)
                .then(() => setLoading(false))
                .catch(() => {
                    setCaptchaPopupOpen(true);
                });
        }
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

    let handleCreate = function () {
        createMonitoring(monitor).then(console.log);
    };

    return (
        <React.Fragment>
            <Fade in={loading}>
                <LinearProgress color="secondary" />
            </Fade>
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
                                    let temp = monitor;
                                    temp.trainNumber = train;
                                    setMonitor(temp);
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
                                openPassengerDialog={setOpenPassengerOptions}
                                loading={loading}
                />
                <CaptchaPopup state={captchaPopup}
                              close={() => {setCaptchaPopupOpen(false); setLoading(false)}}
                              monitor={monitor}
                              renderTrainList={setTrainListOpen}/>

                <AddUsersDialog
                    open={openPassengerOptions}
                    openPassengerDialog={setOpenPassengerOptions}
                    setPassengers={(passengers) => monitor.passengers = passengers}
                    setPlaceFilter={(placeFilter) => monitor.placeFilter = placeFilter}
                    onCreate={handleCreate}
                />
            </Container>
        </React.Fragment>
    );
}