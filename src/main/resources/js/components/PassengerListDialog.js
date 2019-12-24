import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import TextField from '@material-ui/core/TextField';
import Fab from '@material-ui/core/Fab';
import AddIcon from '@material-ui/icons/Add';


const useStyles = makeStyles(theme => ({
    root: {
        width: '100%',
        maxWidth: 500,
        backgroundColor: theme.palette.background.paper,
    },
    nested: {
        paddingLeft: theme.spacing(4),
    },
}));

export default function NestedList() {
    const classes = useStyles();
    const [open, setOpen] = React.useState(true);
    const [passengers, setPassengers] = React.useState([{name: '', surname: ''}]);
    const handleClick = () => {
        setOpen(!open);
    };

    return (<div>
            <List
                component="nav"
                className={classes.root}
            >
                {passengers.map(value => (
                    <ListItem button>
                        <ListItemIcon>
                            <AccountCircleIcon/>
                        </ListItemIcon>
                        <TextField label="Имя" variant="outlined" onChange={(value) => value.name = value}
                                   value={value.name}/>
                        <TextField label="Фамилия" variant="outlined" onChange={(value) => value.surname = value}
                                   value={value.surname}/>
                    </ListItem>))

                }
            </List>
            <Fab color="primary" aria-label="add">
                <AddIcon/>
            </Fab>
        </div>
    );
}
