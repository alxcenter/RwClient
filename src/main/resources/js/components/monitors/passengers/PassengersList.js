import React from 'react';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ConfirmationNumberIcon from '@material-ui/icons/ConfirmationNumber';
import Badge from '@material-ui/core/Badge';


export default function NestedList(props) {

    return (<List>
        {props.passengers.map((passenger, index, array) => (
            <ListItem button>
                <ListItemAvatar>
                    <Badge badgeContent={passenger.status ? "done" : null} color="primary">
                        <AccountCircleIcon fontSize="large"/>
                    </Badge>
                </ListItemAvatar>
                <ListItemText primary={passenger.surname + " " + passenger.name}/>
                <ListItemSecondaryAction>
                    {passenger.status ? (
                        <ConfirmationNumberIcon fontSize="small" color={passenger.status ? "primary" : ""}/>
                    ) : ""}
                </ListItemSecondaryAction>
            </ListItem>
        ))}
    </List>);
};
