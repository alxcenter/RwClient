import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import LinearProgress from "@material-ui/core/LinearProgress";
import Fade from "@material-ui/core/Fade";
import TrainTableRow from "./TrainTableRow";

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    }
});


export default function SimpleTable(props) {

    const classes = useStyles();

    let handleClickMonitoring = function (train) {
        props.setTrain(train.num);
        props.openPassengerDialog(true);
    };

    let handleClickChose = function (train) {
        window.open(`https://booking.uz.gov.ua/ru/?from=${train.from.code}&to=${train.to.code}&date=${train.from.srcDate.split("T")[0]}&train=${train.num}&url=train-wagons`, '_blank');
    };

    return (props.trains != null &&
        <div className="trainTable">
            <Fade
                in={props.loading}
            >
                <LinearProgress color="secondary" />
            </Fade>
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
                            <TrainTableRow train={train}
                            onChose={(train) => handleClickChose(train)}
                            onCreate={(train) => handleClickMonitoring(train)}/>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
}
