import { useState } from "react";
import axios from "axios";

// カスタムフックの作成
export const useFetchUsers = () => {
  const [userList, setUserList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [isError, setIsError] = useState(false);

  // ユーザー取得関数
  const onClickFetchUser = () => {
    setIsLoading(true);
    setIsError(false);

    axios
      .get("http://localhost:8080/users")
      .then((result) => {
        const users = result.data.beans.map((user) => ({
          id: user.id,
          name: `${user.lastname} ${user.firstname}`,
          age: user.age,
        }));
        setUserList(users);
      })
      .catch((e) => {
        setIsError(true);
        console.error(e);
      })
      .finally(() => setIsLoading(false));
  };

  // フックが返す値
  return { userList, isLoading, isError, onClickFetchUser };
};