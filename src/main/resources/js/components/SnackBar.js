import React from 'react';
import Snackbar from '@material-ui/core/Snackbar';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';

export default function SimpleSnackbar(props) {
    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        props.setOpen(false);
    };

    return (
        <div>
            <Snackbar
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'center',
                }}
                open={props.open}
                autoHideDuration={6000}
                onClose={handleClose}
                message={props.message}
                action={
                    <React.Fragment>
                        {/*<Button color="secondary" size="small" onClick={handleClose}>
                            UNDO
                        </Button>*/}
                        <IconButton size="small" aria-label="close" color="inherit" onClick={handleClose}>
                            <CloseIcon fontSize="small" />
                        </IconButton>
                    </React.Fragment>
                }
            />
        </div>
    );
}
