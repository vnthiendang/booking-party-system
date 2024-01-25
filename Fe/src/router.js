import { ROUTER } from "./util/index";
import {
  PackagePage,
  EventPage,
  UserPage,
  ProductsPage,
  LoginPage,
  BlogPage,
  PackagePageHost,
} from "./page";

export const routers = [
  {
    path: ROUTER.PACKAGE,
    element: <PackagePage />,
  },

  {
    path: ROUTER.EVENT,
    element: <EventPage />,
  },
  {
    path: ROUTER.USER,
    element: <UserPage />,
    auth: true,
  },
  {
    path: ROUTER.PRODUCT,
    element: <ProductsPage />,
    auth: true,
  },
  {
    path: ROUTER.BLOG,
    element: <BlogPage />,
    auth: true,
  },
  {
    path: ROUTER.PACKAGE_HOST,
    element: <PackagePageHost />,
    auth: true,
  },
  {
    path: ROUTER.AUTH,
    element: <LoginPage />,
  },
];