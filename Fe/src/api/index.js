import { ROUTER_API } from "../util";
import DemoApi from "./demoApi";
import authApi from "./authApi";
const demoApi = new DemoApi(ROUTER_API.DEMO);
const AuthApi = new authApi(ROUTER_API.AUTH);

export { demoApi, AuthApi };
