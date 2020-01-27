import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import {deleteMonitoring, getMonitorings} from "./MonitoringDataLoader";

export default function AlertDialog(props) {

    React.useEffect(() => {});

    let monitoring = props.monitoring;

    const handleClose = () => {
        props.setOpenConfirmation(false);
    };

    const handleDelete = (id) =>
        deleteMonitoring(id)
            .then(response => console.log("monitoring deleted successful"))
            .then(handleClose)
            .then(() => getMonitorings())
            .then(props.setMonitoringList)
            .then(props.snack("Пользователь удален"));

    return (
        <div>
            <Dialog
                open={props.open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">{"Вы действительно хотите удалить мониторинг?"}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        Подтвердите удаление мониторинга <br/>
                        {monitoring.trainNumber} {monitoring.fromStation.stationName.concat(" > ") + monitoring.toStation.stationName} {monitoring.date}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Отмена
                    </Button>
                    <Button onClick={() => handleDelete(monitoring.id)} color="primary" autoFocus>
                        Подтверждаю
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
