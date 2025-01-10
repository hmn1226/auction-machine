import React from "react";
import ReactDOM from "react-dom/client"; // React 18 のモジュール
import { App } from "./App";
import { AdminFlagProvider } from "./components/providers/AdminFlagProvider";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
    <AdminFlagProvider>
        <App />
    </AdminFlagProvider>
);