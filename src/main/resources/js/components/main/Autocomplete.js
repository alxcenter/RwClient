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
    const loading = open && options.length === 0 && inputValue != '';

    const handleInputChange = event => {
        // console.log(`inputValue is = ${inputValue}`);
        setInputValue(event.target.value);
        console.log(`inputValue is = ${event.target.value}`);
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

    const handleAutocompleteInputChange = (event, value, reason) => {
        if (reason == "clear") {
            props.setState({options: [], text: '', ver: ver});
            props.setStation(null);
            setInputValue('');
            setOptions([]);
        }
    };

    React.useEffect(() => {
        console.log(`${props.autocompleteName} inputvalue is = ${inputValue}`);
        if (props.state!=null){
            console.log(`${props.state.ver} autocomplete ver.${ver}`);
            if (props.version!=ver){
                console.log('do changes');
                setInputValue(props.state.text);
                setOptions(props.state.options);
                setVer(props.version);
            }
        }
    });


    const getStations = (stationName) => {
        if (stationName.length < 3) {
            setOptions([]);
        } else {
            (async () => {
                await sleep(1e3);
                await fetch(`/api/stations/find?name=${stationName}`)
                    .then((response) => response.json())
                    .then(setOptions);
            })();
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
