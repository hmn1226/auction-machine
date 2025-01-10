import { useEffect,useState } from "react";
import { ListItem } from "./components/ListItem";
import axios from "axios";

export const App = ()=>{
  const [users,setUsers] = useState([]);

  useEffect( ()=>{
    axios.get("http://localhost:8080/users").then((res)=>{
        setUsers(res.data.beans);
      })
  } ,[] );

  return (
    <div>
      {users.map(user => (
        <ListItem id={user.id} name={user.name} age={user.age} />
      ))}
    </div>

  );
}