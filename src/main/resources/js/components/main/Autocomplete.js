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
    const [inputValue, setInputValue] = React.useState('lo');
    const loading = open && options.length === 0 && inputValue != null;

    const handleInputChange = event => {
        new Promise((resolve) => {
            if (timer) {
                clearTimeout(timer)
            }

            let text = event.target.value;
            let t = setTimeout(() => resolve(text), 1000);
            setTimer(t);
        })
            .then((obj) => {
                setInputValue(obj);
            });
    };

    const handleAutocompleteChange = function(event, value){
        props.setStation(value);
    };



    React.useEffect(() => {
        let active = true;

        if (inputValue.length < 3) {
            setOptions([]);
            return undefined;
        }
        (async () => {
            const response = await fetch(`http://telega704.io/api/stations/find?name=${inputValue}`);
            await sleep(1e3); // For demo purposes.
            const stations = await response.json();
            if (active) {
                setOptions(stations);
            }
        })();

        return () => {
            active = false;
        };
    }, [inputValue]);

    return (
        <Autocomplete
            onOpen={() => {
                setOpen(true);
            }}
            onClose={() => {
                setOpen(false);
            }}
            open={open}
            id={props.id}
            style={{width: 300}}
            filterOptions={x => x}
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
        />
    );
}
