import { ROUTER } from "./util/index";
import {
  PackagePage,
  EventPage,
  UserPage,
  ProductsPage,
  LoginPage,
  BlogPage,
  PackagePageHost,
  CRUD_Package,
  CRUDPackage,
  RevenueAdminPage,
  RegisterPage,
  OrderBookingPage,
} from "./page";

export const routers = [
  {
    path: ROUTER.PACKAGE,
    element: <PackagePage />,
    // auth: true,
  },

  {
    path: ROUTER.EVENT,
    element: <EventPage />,
    // auth: true,
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
    path: ROUTER.CREATE_PACKAGE_BY_HOST,
    element: <CRUDPackage />,
    auth: true,
  },
  {
    path: ROUTER.EDIT_PACKAGE_BY_HOST,
    element: <CRUDPackage />,
    auth: true,
  },
  {
    path: ROUTER.AUTH,
    element: <LoginPage />,
  },
  {
    path: ROUTER.REVENUE,
    element: <RevenueAdminPage />,
  },
  {
    path: ROUTER.REGISTER,
    element: <RegisterPage />,
  },
  {
    path: ROUTER.ORDER_BOOKING,
    element: <OrderBookingPage />,
  },
];
