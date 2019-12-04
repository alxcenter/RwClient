import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

const useStyles = makeStyles({
    root: {
        width: '100%',
        overflowX: 'auto',
    },
    table: {
        minWidth: 650,
    },
});

function createData(fromStation, toStation, date, trainNumber) {
    return { fromStation, toStation, date, trainNumber };
}

const rows = [
    createData('Frozen yoghurt', 159, 6.0, 24, 4.0),
    createData('Ice cream sandwich', 237, 9.0, 37, 4.3),
    createData('Eclair', 262, 16.0, 24, 6.0),
    createData('Cupcake', 305, 3.7, 67, 4.3),
    createData('Gingerbread', 356, 16.0, 49, 3.9),
];

export default function SimpleTable(props) {
    const classes = useStyles();

    return (
        <Paper className={classes.root} >
            <Table className={classes.table} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Dessert (100g serving)</TableCell>
                        <TableCell align="right">From</TableCell>
                        <TableCell align="right">To</TableCell>
                        <TableCell align="right">Date</TableCell>
                        <TableCell align="right">Train number</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {monitorings.map(monitoring => (
                        <TableRow key={monitoring.id}>
                            <TableCell component="th" scope="row">
                                {monitoring.id}
                            </TableCell>
                            <TableCell align="right">{monitoring.fromStation}</TableCell>
                            <TableCell align="right">{monitoring.toStation}</TableCell>
                            <TableCell align="right">{monitoring.date}</TableCell>
                            <TableCell align="right">{monitoring.trainNumber}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </Paper>
    );
}