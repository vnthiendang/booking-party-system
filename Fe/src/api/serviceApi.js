import Api from "./Api";
import axiosClient from "./axiosClient";

export default class servicdeApi extends Api {
  getListservice() {
    const url = `${this.HOSTNAME}${this.table}/service/getall`;
    return axiosClient.get(url);
  }
}
