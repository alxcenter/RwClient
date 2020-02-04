import React from 'react';
import IconButton from '@material-ui/core/IconButton';
import SwapHorizOutlinedIcon from '@material-ui/icons/SwapHorizOutlined';
import Asynchronous from './Autocomplete';
import Grid from '@material-ui/core/Grid';


export default function StationContainer(props) {
    const [monitor, setMonitor] = React.useState({fromStation: null, toStation: null, date: null});
    const [autocompleteState, setAutocompleteState] = React.useState({from: null, to: null, ver: 0});

    React.useEffect(() => props.setMonitor(monitor), [monitor]);

    let from = (<Asynchronous autocompleteName="Станция отправления"
                              id={"async_from"}
                              snack={props.setSnackBarMessage}
                              version={autocompleteState.ver}
                              state={autocompleteState.from}
                              setState={(state) => {
                                  autocompleteState.from = state
                              }}
                              station={monitor.fromStation}
                              setStation={(station) => {
                                  monitor.fromStation = station;
                              }}/>);

    let to = (<Asynchronous autocompleteName="Станция прибытия"
                            id={"async_to"}
                            snack={props.setSnackBarMessage}
                            version={autocompleteState.ver}
                            state={autocompleteState.to}
                            setState={(state) => {
                                autocompleteState.to = state
                            }}
                            station={monitor.toStation}
                            setStation={(station) => {
                                monitor.toStation = station;
                            }}/>);

    let handleChangeStations = () => {
        console.log("start changing v." + autocompleteState.ver);
        let mon = Object.assign({}, monitor);
        let autoState = Object.assign({}, autocompleteState);
        autoState.from = autocompleteState.to;
        autoState.to = autocompleteState.from;
        autoState.ver = autocompleteState.ver + 1;
        setAutocompleteState(autoState);
        mon.fromStation = monitor.toStation;
        mon.toStation = monitor.fromStation;
        setMonitor(mon);
    };

    return (
        <Grid container direction="row" justify="center" alignItems="center">
            <Grid item>
                {from}
            </Grid>
            <Grid item>
                <IconButton color="secondary" aria-label="Change directions" disableFocusRipple={true}
                            disableRipple={true}
                            onClick={handleChangeStations}>
                    <SwapHorizOutlinedIcon color={"secondary"} fontSize={"large"}/>
                </IconButton>
            </Grid>
            <Grid item>
                {to}
            </Grid>
        </Grid>
    );
}