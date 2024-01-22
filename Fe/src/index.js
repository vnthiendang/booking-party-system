import React, { Suspense } from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { PackagePage, EventPage, UserPage, ProductsPage } from "./page";

const router = createBrowserRouter([
  {
    path: "/",
    element: <PackagePage />,
  },
  {
    path: "/package",
    element: <PackagePage />,
  },
  {
    path: "/event",
    element: <EventPage />,
  },
  {
    path: "/user",
    element: <UserPage />,
  },
  {
    path: "/products",
    element: <ProductsPage />,
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <Suspense>
      <RouterProvider router={router} />
    </Suspense>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
