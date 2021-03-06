import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import {resolveCaptcha} from './trains/TrainSearcher.js'
import LoadingButton from './LoadingButton.js'


export default function CaptchaPopup(props) {
    const [open, setOpen] = React.useState(false);
    const [attempt, setAttempt] = React.useState(0);
    let text = '';
    let captchaUrl = `/captcha?${new Date().getMilliseconds()}`;
    React.useEffect(() => {
        setOpen(props.state)
    });

    const handleChange = (event) => {
        text = event.target.value;
    };

    const handleClose = () => {
        setOpen(false);
        props.close();
    };

    let handleSend = () => {
        return resolve(text);
    };

    let resolve = function () {
        return resolveCaptcha(text)
            .then((response) => {
                console.log(response);
                props.close();
                props.renderTrainList(response);
            })
            .catch(() => {
                text = '';
                setAttempt(attempt + 1);
                document.getElementById("captchaField").value = "";
            });
    };


    return (
        <div>
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">Verification code</DialogTitle>
                <DialogContent>
                    <img src={captchaUrl} alt="lol"/>
                    <DialogContentText>
                        Enter numbers from the picture:
                    </DialogContentText>
                    <TextField
                        id="captchaField"
                        autoFocus
                        margin="dense"
                        type="text"
                        onChange={handleChange}
                        defaultValue={text}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <LoadingButton text="Send" handleClick={handleSend}/>
                </DialogActions>
            </Dialog>
        </div>
    );
}
