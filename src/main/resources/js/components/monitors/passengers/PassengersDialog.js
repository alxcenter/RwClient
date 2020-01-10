import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogTitle from '@material-ui/core/DialogTitle';
import PassengersList from './PassengersList';

export default function AlertDialog(props) {

    const handleClose = () => {
        props.setPassengerListOpen(false);
    };

    return (
        <Dialog
            open={props.passengerListOpen}
            onClose={handleClose}
            scroll={"body"}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogTitle id="alert-dialog-title">Список пассажиров</DialogTitle>
            <PassengersList passengers={props.passengers}/>
            <DialogActions>
                <Button onClick={handleClose} variant="outlined" color="secondary">
                    Закрыть
                </Button>
            </DialogActions>
        </Dialog>
    );
}
