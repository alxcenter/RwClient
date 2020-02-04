import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import CircularProgress from '@material-ui/core/CircularProgress';
import {lightBlue} from '@material-ui/core/colors';
import Button from '@material-ui/core/Button';

const useStyles = makeStyles(theme => ({
    root: {
        display: 'flex',
        alignItems: 'center',
    },
    wrapper: {
        margin: theme.spacing(1),
        position: 'relative',
    },
    buttonProgress: {
        color: lightBlue[500],
        position: 'absolute',
        top: '50%',
        left: '50%',
        marginTop: -12,
        marginLeft: -12,
    },
}));

export default function CircularIntegration(props) {
    const classes = useStyles();
    const [loading, setLoading] = React.useState(false);
    const timer = React.useRef();



    React.useEffect(() => {
        return () => {
            clearTimeout(timer.current);
        };
    }, []);

    const handleButtonClick = () => {
        if (!loading) {
            setLoading(true);
            props.handleClick()
                .then(() => setLoading(false));
        }
    };

    return (
        <div className={classes.root}>
            <div className={classes.wrapper}>
                <Button
                    color="primary"
                    disabled={loading}
                    onClick={handleButtonClick}
                >
                    {props.text}
                </Button>
                {loading && <CircularProgress size={24} className={classes.buttonProgress} />}
            </div>
        </div>
    );
}
