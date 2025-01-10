import React from "react";
import axios from "axios";

const BidButton = () => {
  const onNyusatsuButtonClick = () => {
    axios
      .post("http://localhost:8080/api/bid/abc-defg-hij/1/1", {
        bidPrice: 12000,
      })
      .then((res) => {
        console.log("入札成功:", res.data);
      })
      .catch((error) => {
        console.error("エラー:", error);
      });
  };

  return (
    <button onClick={onNyusatsuButtonClick}>
      入札する
    </button>
  );
};

export default BidButton;