import React from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import MonitoringNavigation from './MonitorNav';
import MonitoringList from './MonitoringList';
import SnackBar from './SnackBar';
import {getMonitorings} from './MonitoringDataLoader';

let navStatusRest = ['ACTIVE', 'PAUSED', 'FINISHED', 'EXPIRED', 'DELETED'];
let navStatus = ['Активные', 'На паузе', 'Завершенные', 'Просроченные', 'Отмененные'];

export default function SimpleContainer() {
    const [monitoringList, setMonitoringList] = React.useState([]);
    const [navIndex, setNavIndex] = React.useState(0);
    const [snackBarOpen, setSnackBarOpen] = React.useState(false);
    const [snackBarMessage, setSnackBarMessage] = React.useState(null);

    let snack = {setSnackBarOpen: setSnackBarOpen, setSnackBarMessage: setSnackBarMessage};

    React.useEffect(() => {
        if (monitoringList.length == 0) {
            getMonitorings().then(setMonitoringList);
        }
    }, [monitoringList]);

    return (
        <React.Fragment>
            <CssBaseline/>
            <Container maxWidth="md">
                <MonitoringNavigation navStatus={navStatus}
                                      setNavIndex={setNavIndex}/>
                <Typography component="div">
                    <MonitoringList
                        monitoringList={monitoringList.filter(x => x.status == navStatusRest[navIndex])}
                        setMonitoringList={setMonitoringList}
                        snack={snack}/>
                </Typography>
            </Container>
            <SnackBar open={snackBarOpen}
                      setOpen={setSnackBarOpen}
            message={snackBarMessage}/>
        </React.Fragment>
    );
}
