import { useState,useContext,memo } from "react";
import { Child1 } from "./components/Child1";
import { Child4 } from "./components/Child4";
import React, { useCallback } from "react";
import { AdminFlagContext } from "./components/providers/AdminFlagProvider";

export const App = ()=>{
    console.log("App レンダリング");

    const[num,setNum] = useState(0);

    const onClickButton = ()=>{
        setNum(num+1);
    }
    const onClickReset = useCallback( ()=>{
        setNum(0);
    },[] );


    //---------------
    const {isAdmin,setIsAdmin} = useContext(AdminFlagContext);
    const onClickSwitch = ()=>setIsAdmin(!isAdmin);

    //------
    const [inputValue,setInputValue] = useState("");

    const handleChange = (event) => {
        setInputValue(event.target.value);
    }

    return (
        <>
            <button onClick={onClickButton}>ボタン</button>
            <p>{num}</p>
            <Child1 onClickReset={onClickReset}/>
            <Child4/>
            <div>
                {isAdmin ? <span>管理者です</span> : <span>管理者以外です</span> }
            </div>
            <button onClick={onClickSwitch}>切替</button>
            <input type="text" value={inputValue} onChange={handleChange} placeholder="値を入力してください"/>
            <p>入力された値:{inputValue}</p>
        </>

    )
}