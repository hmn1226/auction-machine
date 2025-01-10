import React from "react";
import { Fragment } from "react";
import ReactDOM from "react-dom/client"; // React 18 のモジュール
import { useState } from "react";
import { useEffect } from "react";
import { ColoredMessage } from "./components/ColoredMessage";


export const App = () => {

    const [num,setNum] = useState(0);

    useEffect(()=>{
        console.log("init");
    },[]);
    useEffect(()=>{
        console.log("effect");
    },[num]);

    const onClickButton = ()=>{
        setNum(num+1);
    }
    return (
        <Fragment>
            <h1 style={{color:"red"}}>こんにちは!!!ほ</h1>
            <ColoredMessage color="green" message="hello!!!やっほー。">もへもへ</ColoredMessage>
            <button onClick={onClickButton}>ボタン</button>
            <p>{num}</p>
        </Fragment>
    );
};

// ルートを作成してレンダリング
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<App />);