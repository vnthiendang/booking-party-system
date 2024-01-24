import PackagePage from "./PackagePage";
import EventPage from "./EventPage";
import { lazy } from "react";

const BlogPage = lazy(() => import("./blog"));
const UserPage = lazy(() => import("./user"));
const LoginPage = lazy(() => import("./login"));
const ProductsPage = lazy(() => import("./products"));
const Page404 = lazy(() => import("./page-not-found"));
const PackagePageHost = lazy(() => import("./package-host"));
export {
  PackagePage,
  EventPage,
  UserPage,
  BlogPage,
  LoginPage,
  ProductsPage,
  Page404,
  PackagePageHost,
};
