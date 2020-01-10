import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import TrainIcon from '@material-ui/icons/Train';
import PeopleIcon from '@material-ui/icons/People';
import Badge from '@material-ui/core/Badge';
import OptionsButton from './OptionsButton';
import PassengersDialog from './passengers/PassengersDialog';

const useStyles = makeStyles(theme => ({
    root: {
        flexGrow: 1,
        maxWidth: 900,
    },
    title: {
        margin: theme.spacing(4, 0, 2),
    },
}));

export default function InteractiveList(props) {
    const classes = useStyles();
    const [passengerListOpen, setPassengerListOpen] = React.useState(false);
    const [monitors, setMonitors] = React.useState(null);
    const [currentMonitor, setCurrentMonitor] = React.useState(null);

    React.useEffect(() => {
        setMonitors(props.monitoringList);
    });

    const handleClickPassenger = (monitor) => {
        setPassengerListOpen(true);
        setCurrentMonitor(monitor)
    };


    return (
        <div className={classes.root}>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <Typography align={'center'} variant="h6" className={classes.title}>
                        Список мониторингов
                    </Typography>
                    <div className={classes.demo}>
                        <List>
                            {monitors && monitors.map((monitor) =>
                                (<ListItem button>
                                    <ListItemAvatar>
                                        <Avatar>
                                            <TrainIcon/>
                                        </Avatar>
                                    </ListItemAvatar>
                                    <Grid container alignContent={'center'} alignItems={'center'}>
                                        <Grid item xs={2}>
                                            {monitor.trainNumber}
                                        </Grid>
                                        <Grid item xs={5}>
                                            {monitor.fromStation.stationName.concat(" > ") + monitor.toStation.stationName}
                                        </Grid>
                                        <Grid item xs={3}>
                                            {monitor.date}
                                        </Grid>
                                        <Grid item xs={2}>
                                            <IconButton onClick={() => handleClickPassenger(monitor)}>
                                                <Badge badgeContent={monitor.passengers.length} color="secondary">
                                                    <PeopleIcon/>
                                                </Badge>
                                            </IconButton>
                                        </Grid>
                                    </Grid>
                                    <ListItemSecondaryAction>
                                        <OptionsButton monitoring={monitor}
                                                       setMonitoringList={props.setMonitoringList}
                                                       snack={props.snack}/>
                                    </ListItemSecondaryAction>
                                </ListItem>)
                            )}
                        </List>
                    </div>
                </Grid>
            </Grid>
            {currentMonitor && (<PassengersDialog passengerListOpen={passengerListOpen}
                                                  setPassengerListOpen={setPassengerListOpen}
                                                  passengers={currentMonitor.passengers}/>)}

        </div>
    );
}
