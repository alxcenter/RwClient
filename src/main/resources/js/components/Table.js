import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    },
});


export default function SimpleTable(props) {
    const classes = useStyles();

    return (props.trains != null &&
        <TableContainer component={Paper}>
            <Table className={classes.table} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>№ Поезда</TableCell>
                        <TableCell align="left">Откуда<br/>Куда</TableCell>
                        <TableCell align="left">Дата</TableCell>
                        <TableCell align="left">Отправление<br/>Прибытие</TableCell>
                        <TableCell align="left">В пути</TableCell>
                        <TableCell align="left">Свободных мест</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {props.trains.map(train => (
                        <TableRow key={train.num}>
                            <TableCell component="th" scope="row">
                                {train.num}
                            </TableCell>
                            <TableCell align="left">{train.from.station}<br/>{train.to.station}</TableCell>
                            <TableCell align="left">Отправление: {train.from.date}<br/>Прибытие: {train.from.date}</TableCell>
                            <TableCell align="left">{train.from.time}<br/>{train.to.time}</TableCell>
                            <TableCell align="left">{train.travelTime}</TableCell>
                            <TableCell align="left">none</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
