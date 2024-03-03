import Api from "./Api";
import axiosClient from "./axiosClient";

export default class servicdeApi extends Api {
  getListservice() {
    const url = `${this.HOSTNAME}${this.table}/service/getall`;
    return axiosClient.get(url);
  }
  getListpackageByCustomer() {
    const url = `${this.HOSTNAME}${this.table}/packages`;
    return axiosClient.get(url);
  }
  getPackageDetailByCustomer(id) {
    const url = `${this.HOSTNAME}${this.table}/package/${id}`;
    return axiosClient.get(url);
  }
  getListservicePk(id) {
    const url = `${this.HOSTNAME}${this.table}/${id}/services`;
    return axiosClient.get(url);
  }
  checkTime(body) {
    const url = `${this.HOSTNAME}${this.table}/checkPackageAvailableInDateRange`;
    return axiosClient.post(url, body);
  }
  checkMail(mail) {
    const url = `${this.HOSTNAME}${this.table}/checkEmail?email=${mail}`;
    return axiosClient.get(url);
  }
  bookPackage(body) {
    const url = `${this.HOSTNAME}${this.table}/bookPackage`;
    return axiosClient.post(url, body);
  }
  cancelBooking(body) {
    const url = `${this.HOSTNAME}${this.table}/cancelBooking`;
    return axiosClient.post(url, body);
  }
  getBookingHistory() {
    const url = `${this.HOSTNAME}${this.table}/history`;
    return axiosClient.get(url);
  }
  getOrderDetail(id) {
    const url = `${this.HOSTNAME}${this.table}/listOrderDetails/${id}`;
    return axiosClient.get(url);
  }
  getProfile() {
    const url = `${this.HOSTNAME}/profile`;
    return axiosClient.get(url);
  }
  addService(body) {
    const url = `${this.HOSTNAME}${this.table}/addServices`;
    return axiosClient.post(url, body);
  }
}
