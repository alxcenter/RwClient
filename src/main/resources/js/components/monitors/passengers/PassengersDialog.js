import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

export default function AlertDialog(props) {

    const handleClose = () => {
        props.openPassengerDialog(false);
    };

    return (
        <Dialog
            open={props.open}
            onClose={handleClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
            scroll="body"
        >
            <DialogTitle id="alert-dialog-title">Some Title</DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-description">
                    Список пассажиров
                </DialogContentText>
            </DialogContent >
            <DialogActions>
                <Button onClick={handleClose} variant="outlined" color="secondary">
                    Закрыть
                </Button>
            </DialogActions>
        </Dialog>
    );
}
