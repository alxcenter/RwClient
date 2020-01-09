import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import PassengerListDialog from './PassengerListDialog';
import PlaceFilterForm from './PlaceFilterForm';

export default function AlertDialog(props) {

    const [passengers, setPassengers] = React.useState([]);
    const [placeFilter, setPlaceFilter] = React.useState({});


    const handleClose = () => {
        props.openPassengerDialog(false);
    };

    const handleCreate = () => {
        console.log(placeFilter);
        console.log(passengers);
        props.setPlaceFilter(placeFilter);
        props.setPassengers(passengers);
        props.openPassengerDialog(false);
        props.onCreate();
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
                        Укажите пассажиров для которых будет создан мониторинг
                    </DialogContentText>
                    <PlaceFilterForm setPlaceFilter={setPlaceFilter}/>
                    <PassengerListDialog setPassengers={setPassengers}/>
                </DialogContent >
                <DialogActions>
                    <Button onClick={handleClose} variant="outlined" color="secondary">
                        Отменить
                    </Button>
                    <Button onClick={handleClose} variant="contained" color="secondary" autoFocus
                    onClick={handleCreate}>
                        Создать мониторинг
                    </Button>
                </DialogActions>
            </Dialog>
    );
}
