import Api from "./Api";
import axiosClient from "./axiosClient";

export default class hostApi extends Api {
  createPackage(body) {
    const url = `${this.HOSTNAME}${this.table}/createPackage`;
    console.log("url", body, url);
    return axiosClient.post(url, body);
  }
  getListPackage() {
    const url = `${this.HOSTNAME}${this.table}/packages`;
    return axiosClient.get(url);
  }
  getListLocation() {
    const url = `${this.HOSTNAME}${this.table}/package/locations`;
    return axiosClient.get(url);
  }
}
