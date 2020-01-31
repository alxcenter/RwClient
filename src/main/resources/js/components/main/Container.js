import React from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import Container from '@material-ui/core/Container';
import MaterialUIPickers from './DateUI';
import ContainedButtons from './SearchButton';
import Grid from '@material-ui/core/Grid';
import TrainListTable from './trains/TrainTable';
import CaptchaPopup from './CaptchaDialog';
import {createMonitoring, getTrainList} from './trains/TrainSearcher'
import AddUsersDialog from "./passengers/AddUsersDialog";
import LinearProgress from "@material-ui/core/LinearProgress";
import Fade from "@material-ui/core/Fade";
import SnackBar from '../SnackBar';
import StationContainer from './StationContainer';
import {CaptchaEx} from "../exceptions/CaptchaEx"


export default function FixedContainer() {
    const [captchaPopup, setCaptchaPopupOpen] = React.useState(false);
    const [trainList, setTrainListOpen] = React.useState(null);
    const [openPassengerOptions, setOpenPassengerOptions] = React.useState(false);
    const [monitor, setMonitor] = React.useState({fromStation: null, toStation: null, date: null});
    const [loading, setLoading] = React.useState(false);
    const [snackBarOpen, setSnackBarOpen] = React.useState(false);
    const [snackBarMessage, setSnackBarMessage] = React.useState(null);

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
                .catch((error) => {
                    if (error instanceof CaptchaEx) {
                        setCaptchaPopupOpen(true);
                        console.error(error.message);
                    }else {
                        handleSetSnackBarMessage(error.message);
                        setLoading(false);
                    }
                });
        }
    };

    let handleSetPassengers = (passengers) => {
        let temp = monitor;
        temp.passengers = passengers;
        setMonitor(temp);
    };

    let handleSetSnackBarMessage = (message) => {
      setSnackBarMessage(message);
      setSnackBarOpen(message);
    };

    let handleSetPlaceFilter = (placeFilter) => {
        let temp = monitor;
        temp.placeFilter = placeFilter;
        setMonitor(temp);
    };

    let handleSetTrain = (train) => {
        let temp = monitor;
        temp.trainNumber = train;
        setMonitor(temp);
    };

    let handleCreate = function () {
        createMonitoring(monitor)
            .then(console.log)
            .then(() => setSnackBarOpen(true))
            .then(() => setSnackBarMessage("Мониторинг успешно создан"))
            .then(() => setTrainListOpen(null));
    };



    return (
        <React.Fragment>
            <Fade in={loading}>
                <LinearProgress color="secondary"/>
            </Fade>
            <CssBaseline/><br/>
            <Container fixed>
                <StationContainer
                    setSnackBarMessage={handleSetSnackBarMessage}
                    setMonitor={setMonitor}/>
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
                                setTrain={handleSetTrain}
                                setPassengers={handleSetPassengers}
                                setPlaceFilter={handleSetPlaceFilter}
                                openPassengerDialog={setOpenPassengerOptions}
                                loading={loading}
                />
                <CaptchaPopup state={captchaPopup}
                              close={() => {
                                  setCaptchaPopupOpen(false);
                                  setLoading(false)
                              }}
                              monitor={monitor}
                              renderTrainList={setTrainListOpen}/>

                <AddUsersDialog
                    open={openPassengerOptions}
                    openPassengerDialog={setOpenPassengerOptions}
                    setPassengers={(passengers) => monitor.passengers = passengers}
                    setPlaceFilter={(placeFilter) => monitor.placeFilter = placeFilter}
                    onCreate={handleCreate}
                    snack={handleSetSnackBarMessage}
                />
            </Container>
            <SnackBar open={snackBarOpen}
                      setOpen={setSnackBarOpen}
                      message={snackBarMessage}/>
        </React.Fragment>
    );
}