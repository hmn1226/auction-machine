export const ListItem = props=>{
    const {id,name,age} = props;
    return (
        <p>
            {id} : {name} ({age})
        </p>
    )
}