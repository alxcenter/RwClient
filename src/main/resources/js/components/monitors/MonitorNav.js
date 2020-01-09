import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import BottomNavigation from '@material-ui/core/BottomNavigation';
import BottomNavigationAction from '@material-ui/core/BottomNavigationAction';
import FavoriteIcon from '@material-ui/icons/Favorite';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';
import TimerOffIcon from '@material-ui/icons/TimerOff';
import CancelIcon from '@material-ui/icons/Cancel';
import PauseCircleFilledIcon from '@material-ui/icons/PauseCircleFilled';

const useStyles = makeStyles({
    root: {
    }
});

export default function MonitoringNavigation(props) {
    const classes = useStyles();
    const [value, setValue] = React.useState(0);

    React.useEffect(() => {
        console.log(value);
        props.setNavIndex(value);
    }, [value]);

    return (
        <BottomNavigation
            value={value}
            onChange={(event, newValue) => {
                setValue(newValue);
            }}
            showLabels
            className={classes.root}
        >
            <BottomNavigationAction label={props.navStatus[0]} icon={<FavoriteIcon />} />
            <BottomNavigationAction label={props.navStatus[1]} icon={<PauseCircleFilledIcon />} />
            <BottomNavigationAction label={props.navStatus[2]} icon={<CheckCircleIcon />} />
            <BottomNavigationAction label={props.navStatus[3]} icon={<TimerOffIcon />} />
            <BottomNavigationAction label={props.navStatus[4]} icon={<CancelIcon />} />

        </BottomNavigation>
    );
}
