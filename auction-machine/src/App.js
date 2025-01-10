import React, { useEffect, useState } from "react";
import BidButton from "./components/BidButton";
import WebSocket from "./components/WebSocket";

function App() {


  return (
    <>
      <BidButton />
      <WebSocket />
    </>
  );
}

export default App;
