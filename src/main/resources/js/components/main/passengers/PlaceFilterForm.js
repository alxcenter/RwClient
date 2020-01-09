import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import FormLabel from '@material-ui/core/FormLabel';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@material-ui/core/FormGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormHelperText from '@material-ui/core/FormHelperText';
import Checkbox from '@material-ui/core/Checkbox';

const useStyles = makeStyles(theme => ({
    root: {
        display: 'flex',
    },
    formControl: {
        margin: theme.spacing(3),
    },
}));
export default function CheckboxesGroup(props) {
    const classes = useStyles();
    const [state, setState] = React.useState({
        wagon_l_type: false,
        wagon_k_type: true,
        wagon_p_type: true,
    });

    React.useEffect(() => {props.setPlaceFilter(state)});

    const handleChange = name => event => {
        setState({ ...state, [name]: event.target.checked });
        props.setPlaceFilter(state);
    };

    const { wagon_l_type, wagon_k_type, wagon_p_type } = state;
    const error = [wagon_l_type, wagon_k_type, wagon_p_type].filter(v => v).length == 0;

    return (
        <div className={classes.root}>

            <FormControl required error={error} component="fieldset" className={classes.formControl}>
                <FormLabel component="legend">Типы вагонов:</FormLabel>
                <FormGroup row>
                    <FormControlLabel
                        control={<Checkbox checked={wagon_l_type} onChange={handleChange('wagon_l_type')} value="gilad" />}
                        label="Люкс"
                    />
                    <FormControlLabel
                        control={<Checkbox checked={wagon_k_type} onChange={handleChange('wagon_k_type')} value="jason" />}
                        label="Купе"
                    />
                    <FormControlLabel
                        control={
                            <Checkbox checked={wagon_p_type} onChange={handleChange('wagon_p_type')} value="antoine" />
                        }
                        label="Плацкарт"
                    />
                </FormGroup>
                <FormHelperText>{error?"Выберите хотябы один тип вагонов для отслеживания":""}</FormHelperText>
            </FormControl>
        </div>
    );
}
