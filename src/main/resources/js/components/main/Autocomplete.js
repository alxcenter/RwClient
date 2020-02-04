import 'isomorphic-fetch';
import React from 'react';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import CircularProgress from '@material-ui/core/CircularProgress';

function sleep(delay = 0) {
    return new Promise(resolve => {
        setTimeout(resolve, delay);
    });
}

export default function Asynchronous(props) {
    const [open, setOpen] = React.useState(false);
    const [options, setOptions] = React.useState([]);
    const [timer, setTimer] = React.useState(null);
    const [inputValue, setInputValue] = React.useState('');
    const [ver, setVer] = React.useState(0);
    const loading = open && timer && inputValue != '';

    const handleInputChange = event => {
        setInputValue(event.target.value);
        let text = event.target.value;
        props.setState({options: options, text: text, ver: ver});
        new Promise((resolve) => {
            timer ? clearTimeout(timer) : undefined;
            let t = setTimeout(() => resolve(getStations(text)), 1000);
            setTimer(t);
        })
            .then(props.setState({options: options, text: text, ver: ver}));
    };

    const handleAutocompleteChange = function (event, value) {
        if (value) {
            props.setStation(value);
            setInputValue(value.stationName);
            props.setState({options: options, text: value.stationName, ver: ver});
        }
    };

    const handleSetOptions = (opt) => {
        if (opt.length == 0) {
            console.log("Нет такой станции");
            props.snack("Нет такой станции");
            setOpen(false);
        } else {
            setOptions(opt);
        }
    };

    const handleAutocompleteInputChange = (event, value, reason) => {
        if (reason == "clear") {
            props.setState({options: [], text: '', ver: ver});
            props.setStation(null);
            setInputValue('');
            setOptions([]);
        }
    };

    React.useEffect(() => {
        if (props.state != null) {
            if (props.version != ver) {
                console.log('do changes');
                setInputValue(props.state.text);
                setOptions(props.state.options);
                setVer(props.version);
            }
        }
    });


    const getStations = (stationName, isFirstInit) => {
        if (stationName.length > 2 || isFirstInit) {
            (async () => {
                await sleep(1e3);
                await fetch(`/api/stations/find?name=${stationName}`)
                    .then((response) => {
                        if (response.ok) {
                            return response.json();
                        } else {
                            response.json()
                                .then(errObject => {
                                    console.log(errObject.message);
                                    props.snack("Отпали прокси." + errObject.message);
                                })
                        }
                    })
                    .then(handleSetOptions)
                    .then(setTimer);
            })();
        } else {
            setOptions([]);
        }
    };

    return (
        <Autocomplete
            onOpen={() => setOpen(true)}
            onClose={() => setOpen(false)}
            open={open}
            id={props.id}
            style={{width: 300}}
            filterOptions={x => x}
            inputValue={inputValue}
            options={options}
            getOptionLabel={option => option.stationName}
            autoComplete={false}
            includeInputInList
            freeSolo={true}
            loading={loading}
            renderInput={params => (
                <TextField
                    {...params}
                    label={props.autocompleteName}
                    variant="outlined"
                    fullWidth
                    InputProps={{
                        ...params.InputProps, endAdornment: (<React.Fragment>
                                {loading ? <CircularProgress color="inherit" size={10}/> : null}
                                {params.InputProps.endAdornment}
                            </React.Fragment>
                        ),
                    }}
                    onChange={handleInputChange}
                />
            )}
            onChange={handleAutocompleteChange}
            onInputChange={handleAutocompleteInputChange}
        />
    );
}
