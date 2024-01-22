import { ROUTER_API } from "../util";
import DemoApi from "./demoApi";

const demoApi = new DemoApi(ROUTER_API.DEMO);

export { demoApi };
