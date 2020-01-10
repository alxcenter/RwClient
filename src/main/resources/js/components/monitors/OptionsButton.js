import React from 'react';
import {withStyles} from '@material-ui/core/styles';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Edit from '@material-ui/icons/Edit';
import Delete from '@material-ui/icons/Delete';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import IconButton from '@material-ui/core/IconButton';
import PauseCircleFilledIcon from '@material-ui/icons/PauseCircleFilled';
import {deleteMonitoring} from "./MonitoringDataLoader";
import DeleteConfirmPopup from "./DeleteConfirmPopup";

const StyledMenu = withStyles({
    paper: {
        border: '1px solid #d3d4d5',
    },
})(props => (
    <Menu
        elevation={0}
        getContentAnchorEl={null}
        anchorOrigin={{
            vertical: 'center',
            horizontal: 'center',
        }}
        transformOrigin={{
            vertical: 'top',
            horizontal: 'right',
        }}
        {...props}
    />
));

const StyledMenuItem = withStyles(theme => ({
    root: {
        '&:focus': {
            backgroundColor: theme.palette.primary.main,
            '& .MuiListItemIcon-root, & .MuiListItemText-primary': {
                color: theme.palette.common.white,
            },
        },
    },
}))(MenuItem);

export default function CustomizedMenus(props) {
    const [anchorEl, setAnchorEl] = React.useState(null);
    const [openConfirmation, setOpenConfirmation] = React.useState(false);

    const handleClick = event => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleDelete = (id) =>
        deleteMonitoring(id)
            .then(response => console.log("monitoring deleted successful"))
            .then(handleClose);

    return (
        <div>
            <IconButton
                aria-label="more"
                aria-controls="long-menu"
                aria-haspopup="true"
                onClick={handleClick}
            >
                <MoreVertIcon/>
            </IconButton>
            <StyledMenu
                id="customized-menu"
                anchorEl={anchorEl}
                keepMounted
                open={Boolean(anchorEl)}
                onClose={handleClose}
            >
               {/* <StyledMenuItem>
                    <ListItemIcon>
                        <Edit fontSize="small"/>
                    </ListItemIcon>
                    <ListItemText primary="Редактировать"/>
                </StyledMenuItem>
                <StyledMenuItem>
                    <ListItemIcon>
                        <PauseCircleFilledIcon fontSize="small"/>
                    </ListItemIcon>
                    <ListItemText primary="Поставить на паузу"/>
                </StyledMenuItem>*/}
                <StyledMenuItem onClick={() => {
                    handleClose();
                    setOpenConfirmation(true);
                }}>
                    <ListItemIcon>
                        <Delete fontSize="small"/>
                    </ListItemIcon>
                    <ListItemText primary="Удалить"/>
                </StyledMenuItem>
            </StyledMenu>
            <DeleteConfirmPopup
                snack={props.snack}
                monitoring={props.monitoring}
                open={openConfirmation}
                setOpenConfirmation={setOpenConfirmation}
                setMonitoringList={props.setMonitoringList}/>
        </div>
    );
}
