import { ROUTER } from "./util/index";
import {
  PackagePage,
  EventPage,
  UserPage,
  ProductsPage,
  LoginPage,
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
    path: ROUTER.AUTH,
    element: <LoginPage />,
  },
];
