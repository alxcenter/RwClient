import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import TextField from '@material-ui/core/TextField';
import Fab from '@material-ui/core/Fab';
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import Grid from '@material-ui/core/Grid';
import IconButton from '@material-ui/core/IconButton';
import PersonAddIcon from '@material-ui/icons/PersonAdd';


const useStyles = makeStyles(theme => ({
    root: {
        width: '100%',
        maxWidth: 600,
        backgroundColor: theme.palette.background.paper,
    },
    nested: {
        paddingLeft: theme.spacing(4),
    },
}));

export default function NestedList(props) {
    const classes = useStyles();
    const [, setState] = React.useState({});
    const [open, setOpen] = React.useState(true);
    const [passengers, setPassengers] = React.useState([{}]);
    const [addVisible, setAddVisible] = React.useState(true);
    const handleClick = () => {
        setOpen(!open);
    };

    let handleAddClick = function () {
        passengers.push({});
        setState({});
        if (passengers.length >= 8) {
            setAddVisible(false);
        }
    };

    let handleDelete = function (index) {
        if (passengers.length > 1) {
            passengers.splice(index, 1);
            setState({});
            if (!addVisible) {
                setAddVisible(true)
            }
        } else {
            setPassengers([{}]);
        }
        props.setPassengers(passengers.filter(x => x.name && x.surname));
    };

    let handleInput = function (value, index) {
        console.log(value);
        passengers[index] = value;
        props.setPassengers(passengers.filter(x => x.name && x.surname));
    };

    return (<div>
        <Grid container direction="column" justify="center" alignItems="center" spacing={5}>
            <Grid item>
                {passengers.map((value, index, array) => (
                    <Grid container direction="row" justify="center" alignItems="center" spacing={2}>
                        <Grid item>
                            <AccountCircleIcon fontSize="large"/>
                        </Grid>
                        <Grid item>
                            <TextField label="Фамилия"
                                       onChange={(event) => {
                                           value.surname = event.target.value;
                                           handleInput(value, index);
                                       }}/>

                        </Grid>
                        <Grid item>
                            <TextField label="Имя"
                                       onChange={(event) => {
                                           value.name = event.target.value;
                                           handleInput(value, index);
                                       }}/>
                        </Grid>
                        <Grid item>
                            <IconButton aria-label="delete" className={classes.margin}
                                        onClick={() => handleDelete(index)}>
                                <DeleteIcon fontSize="small"/>
                            </IconButton>
                        </Grid>
                    </Grid>
                ))}

            </Grid>

            {addVisible &&
            (<Grid item>
                <Fab color="secondary" aria-label="add" onClick={handleAddClick} size="small">
                    <PersonAddIcon/>
                </Fab>
            </Grid>)
            }
        </Grid>
    </div>);
};
