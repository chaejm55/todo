import React, { useEffect, useRef, useState } from "react";
import { ListItem, ListItemText, InputBase, Checkbox, ListItemSecondaryAction, IconButton } from "@material-ui/core";
import { DeleteOutlined } from "@material-ui/icons";

const Todo = (props) => {
    // constructor use hook
    const [item, setItem] = useState(props.item);
    const [readOnly, setReadOnly] = useState(true);
    const deleteItem = props.deleteItem;
    const editItem = props.editItem;
    const mounted = useRef(false);

    const deleteEventHandler = () => {
        deleteItem(item);
    };

    const offReadOnlyMode = () => {
        setReadOnly(false);
    };

    const enterKeyEventHandler = (e) => {
        if (e.key == 'Enter') {
            setReadOnly(true);
        }
    };

    const editEventHandler = (e) => {
        item.title = e.target.value;
        editItem(item);
    }

    const checkBoxEventHandler = (e) => {
        item.done = !item.done;
        editItem(item);
    }

    // readOnly 변화 관찰
    useEffect(() => {
        if (!mounted.current) {
            mounted.current = true;
        }
        else {
            console.log("ReadOnly? ", readOnly);
        }
    }, [readOnly]);

    return (
        <ListItem>
            <Checkbox checked = {item.done} onClick={checkBoxEventHandler} disableRipple />
            <ListItemText>
                <InputBase
                    inputProps = {{ 
                        "aria-label": "naked",
                        readOnly: readOnly
                    }}
                    onClick={offReadOnlyMode} // 클릭 시 readOnly false, 수정 가능
                    onKeyPress={enterKeyEventHandler} // 엔터, 리턴 키 누를 시 readOnly true, 수정 불가능
                    onChange={editEventHandler} // 수정 시 내용 저장
                    type="text"
                    id={item.id}
                    name={item.id}
                    value={item.title}
                    multiline={true}
                    fullwidth={true}
                    />
            </ListItemText>
            
            <ListItemSecondaryAction>
                <IconButton aria-label="Delete Todo" onClick={deleteEventHandler}>
                    <DeleteOutlined />
                </IconButton>
            </ListItemSecondaryAction>
        </ListItem>
    );
}

export default Todo;