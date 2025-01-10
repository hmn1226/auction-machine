import { useState,memo } from "react";

const style = {
    backgroundColor : "lightgray"
}

export const Child2 = memo(()=>{
    console.log("Child2レンダリング");

    const [selectedValue, setSelectedValue] = useState("");

    const handleChange = (event) => {
        setSelectedValue(event.target.value);
    };

    
    return (
        <div style={style}>
            <p>Child2</p>

            <div>
                <h3>好きな色を選んでください:</h3>
                <div>
                    <input 
                    type="radio" 
                    id="red" 
                    name="color" 
                    value="Red" 
                    onChange={handleChange} 
                    />
                    <label htmlFor="red">Red</label>
                </div>
                <div>
                    <input 
                    type="radio" 
                    id="blue" 
                    name="color" 
                    value="Blue" 
                    onChange={handleChange} 
                    />
                    <label htmlFor="blue">Blue</label>
                </div>
                <div>
                    <input 
                    type="radio" 
                    id="green" 
                    name="color" 
                    value="Green" 
                    onChange={handleChange} 
                    />
                    <label htmlFor="green">Green</label>
                </div>
                <p>選択された色: {selectedValue}</p>
                </div>
        </div>

    );
});