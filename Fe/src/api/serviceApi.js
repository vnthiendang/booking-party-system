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
  bookPackage(body, token) {
    const url = `${this.HOSTNAME}${this.table}/bookPackage`;
    const headers = {
      Authorization: `Bearer ${token}`,
    };
    return axiosClient.post(url, body, {
      headers,
    });
  }
  cancelBooking(id, token) {
    const url = `${this.HOSTNAME}${this.table}/cancelBooking/${id}`;
    const headers = {
      Authorization: `Bearer ${token}`,
    };
    return axiosClient.post(url, {
      headers,
    });
  }
  getBookingHistory(token) {
    const url = `${this.HOSTNAME}${this.table}/history`;
    const headers = {
      Authorization: `Bearer ${token}`,
    };
    return axiosClient.get(url, {
      headers,
    });
  }
  getOrderDetail(token) {
    const url = `${this.HOSTNAME}${this.table}/history`;
    const headers = {
      Authorization: `Bearer ${token}`,
    };
    return axiosClient.get(url, {
      headers,
    });
  }
  getProfile() {
    const url = `${this.HOSTNAME}/profile`;
    return axiosClient.get(url);
  }
  addService(body, token) {
    const url = `${this.HOSTNAME}${this.table}/addServices`;
    const headers = {
      Authorization: `Bearer ${token}`,
    };
    return axiosClient.post(url, body, {
      headers,
    });
  }
}
