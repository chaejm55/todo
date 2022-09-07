import Todo from './Todo';
import './App.css';
import { useEffect, useState } from 'react';
import { Container, List, Paper } from '@material-ui/core';
import AddTodo from './AddTodo';
import { call } from "./service/ApiService"

function App() {
  const [items, setItems] = useState([]);

    // 컴포넌트 생성 시([]) 마다 아이템 갱신
    useEffect(() => {
      call("/todo", "GET", null)
      .then((response) => setItems(response.data));
    }, []);
    
  const addItem = (item) => {
    call("/todo", "POST", item)
    .then((response) => setItems(response.data));
  };

  const editItem = (item) => {
    call("/todo", "PUT", item)
    .then((response) => setItems(response.data));
  }

  const deleteItem = (item) => {
    call("/todo", "DELETE", item)
    .then((response) => setItems(response.data));
  };


  /*
  const editItem = () => { // app.js에서 items 접근
    setItems([...items]);
  };*/

  /*
  // items 리렌더링 될때마다 변화된 값 출력
  useEffect(() => {
    console.log("Updated Items : ", items);
  }, [items]);
  */



  let todoItems = items.length > 0 && (
    <Paper style={{ margin: 16 }}>
      <List>
        {items.map((item, idx) => (
          <Todo item ={item} key={item.id} deleteItem={deleteItem} editItem={editItem}/>
        ))}
      </List>
    </Paper>
  );

  return (
    <div className="App">
      <Container maxWidth="md">
        <AddTodo addItem={addItem}/>
        <div className="TodoList">{todoItems}</div>
      </Container>
    </div>
  );
}

export default App;
