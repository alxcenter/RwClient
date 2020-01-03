import TableCell from "@material-ui/core/TableCell";
import Grid from "@material-ui/core/Grid";
import ButtonGroup from "@material-ui/core/ButtonGroup";
import Button from "@material-ui/core/Button";
import TableRow from "@material-ui/core/TableRow";
import Skeleton from '@material-ui/lab/Skeleton';
import React from "react";

export default function SimpleTable(props) {

    let train = props.train;

    return <TableRow key={train.num}>
        <TableCell component="th" scope="row">
            {train.num}
        </TableCell>
        <TableCell align="left">{train.from.station}<br/>{train.to.station}</TableCell>
        <TableCell align="left">Отправление: {train.from.date}<br/>Прибытие: {train.from.date}
        </TableCell>
        <TableCell align="left">{train.from.time}<br/>{train.to.time}</TableCell>
        <TableCell align="left">{train.travelTime}</TableCell>
        <TableCell align="left">
            <Grid container direction="column" justify="center" spacing={3}>
                {train.types.length > 0 ? train.types.map((type, index) => (
                    <Grid item >
                        <Grid container direction="row" justify="center" wrap={'nowrap'}>
                            <Grid item xs={2}>
                                {type.id}
                            </Grid>
                            <Grid className="free-places" item xs={2}>
                                {type.places}
                            </Grid>
                            <Grid item>
                                {type.id.length<3?(<ButtonGroup key={index} variant="contained" size="small"
                                                                 color="secondary">
                                <Button onClick={() => props.onChose()}>
                                    Выбрать
                                </Button>
                                <Button color="primary"
                                        onClick={() => props.onCreate(train)}>
                                    Отслеживать
                                </Button>
                            </ButtonGroup>):(<Skeleton variant="rect" width={150} height={30} />)}
                            </Grid>
                        </Grid>
                    </Grid>
                )) : (
                    <Grid item>
                        <Button color="primary" variant="contained" size="small" fullWidth={true}
                                onClick={() => props.onCreate(train)}>
                            Отслеживать
                        </Button>
                    </Grid>)}
            </Grid>
        </TableCell>
    </TableRow>
}