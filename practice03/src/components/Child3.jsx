import { memo,useContext } from "react";
import { AdminFlagContext } from "./providers/AdminFlagProvider";

const style = {
    backgroundColor : "lightgray"
}

export const Child3 = memo(()=>{
    console.log("Child3レンダリング");

    const contextValue = useContext(AdminFlagContext);
    console.log(contextValue);
    const {isAdmin,setIsAdmin} = useContext(AdminFlagContext);

    return (
        <div style={style}>
            <p>Child3</p>
            {isAdmin ? <span>管理者です</span> : <span>管理者以外です</span> }
        </div>
    );
});