import 'date-fns';
import React from 'react';
import Grid from '@material-ui/core/Grid';
import DateFnsUtils from "@date-io/date-fns";
import {KeyboardDatePicker, MuiPickersUtilsProvider,} from '@material-ui/pickers';

export default function MaterialUIPickers(props) {
    // The first commit of Material-UI
    const [selectedDate, setSelectedDate] = React.useState(new Date());
    let getMaxDate = () => {
        let date = new Date();
        date.setDate(date.getDate() + 30);
        return date;
    };

    React.useEffect(() => {
        props.setDate(selectedDate);
    });




    const handleDateChange = date => {
        props.setDate(date);
        setSelectedDate(date);
    };

    return (
        <MuiPickersUtilsProvider utils={DateFnsUtils}>
            <Grid container justify="flex-start">
                <KeyboardDatePicker
                    disableToolbar
                    variant="inline"
                    format="yyyy-MM-dd"
                    margin="normal"
                    id="date-picker-inline"
                    label="Дата отправления"
                    value={selectedDate}
                    onChange={handleDateChange}
                    KeyboardButtonProps={{
                        'aria-label': 'change date',
                    }}
                    minDate={new Date()}
                    maxDate={getMaxDate()}
                />
            </Grid>
        </MuiPickersUtilsProvider>
    );
}
