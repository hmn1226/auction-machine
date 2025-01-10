import { useState } from "react";
import { useFetchUsers } from "./hooks/useFetchUsers"; // カスタムフックをインポート

const App = () => {
  // カスタムフックを呼び出して、必要な値を取得
  const { userList, isLoading, isError, onClickFetchUser } = useFetchUsers();

  return (
    <div>
      <button onClick={onClickFetchUser}>ユーザー取得</button>
      {isError && <p style={{ color: "red" }}>エラーが発生しました</p>}

      {isLoading ? (
        <p>データ取得中...</p>
      ) : (
        userList.map((user) => (
          <p key={user.id}>
            {`${user.id} : ${user.name} (${user.age}歳)`}
          </p>
        ))
      )}
    </div>
  );
};

export default App;