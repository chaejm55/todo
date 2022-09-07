import { Button, Grid, Paper, TextField } from "@material-ui/core";
import React, { useState } from "react";

const AddTodo = (props) => {
    const [item, setItem] = useState({ title: "" });
    const addItem = props.addItem;

    const onInputChange = (e) => {
        setItem({title: e.target.value});
        console.log(item)
    };

    const onButtonClick = () => {
        addItem(item); // addItem 함수 사용
        setItem({ title: "" });
    };

    const enterKeyEventHandler = (e) => {
        if (e.key == 'Enter') {
            onButtonClick();
        }
    };

    return (
        <Paper style={{ margin:16, padding:16 }}>
            <Grid container>
                <Grid xs={11} md={11} item style={{ paddingRight: 16 }}>
                    <TextField placeholder="Add Todo here" fullWidth onChange={onInputChange} onKeyPress={enterKeyEventHandler} value={item.title}/>
                </Grid>
                <Grid xs={1} md={1} item>
                    <Button fullWidth color="secondary" variant="outlined" onClick={onButtonClick}>
                        +
                    </Button>
                </Grid>
            </Grid>
        </Paper>
    );
}

export default AddTodo;