import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    }


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
                            <TableCell align="left">Отправление: {train.from.date}<br/>Прибытие: {train.from.date}
                            </TableCell>
                            <TableCell align="left">{train.from.time}<br/>{train.to.time}</TableCell>
                            <TableCell align="left">{train.travelTime}</TableCell>
                            <TableCell align="left">
                                <Grid
                                    container
                                    direction="column"
                                    justify="center"
                                    spacing={3}
                                >
                                    {train.types.length > 0 ? train.types.map((type, index) => (
                                        <Grid item>
                                            <Grid
                                                container
                                                direction="row"
                                                justify="center"
                                                alignItems="flex-start"
                                                spacing={1}
                                            >
                                                <Grid item xs={2}>
                                                    {type.id}
                                                </Grid>
                                                <Grid className="free-places" item xs={2}>
                                                    {type.places}
                                                </Grid>
                                                <Grid item xs={7} alignText="left">
                                                    <ButtonGroup key={index}  variant="contained" size="small" color="secondary">
                                                    <Button>
                                                        Выбрать
                                                    </Button>
                                                    <Button color="primary">
                                                        Отслеживать
                                                    </Button>
                                                    </ButtonGroup>
                                                </Grid>
                                                <Grid item xs={1}>
                                                </Grid>
                                            </Grid>
                                        </Grid>
                                    )) : (<Button variant="contained" size="small" color="primary">
                                        Отслеживать
                                    </Button>)}
                                </Grid>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
