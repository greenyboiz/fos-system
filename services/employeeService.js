import axios from 'axios';
import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

Vue.use(VueToast, { position: 'top' });
class EmployeeService {
  async getDishes(header) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/dishes', header);

    return res.data;
  }

  async getTables(header) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/tables', header);

    return res.data;
  }

  async getOrderByTableId(tableId, header) {
    const res = await axios.get(`https://project-for-fos-mld.herokuapp.com/api/tableByOrder/${tableId}`, header);

    return res.data;
  }

  async getPaymentByOrderId(orderId, header) {
    const res = await axios.get(`https://project-for-fos-mld.herokuapp.com/api/paymentOfTable/${orderId}`, header);

    return res.data;
  }

  async switchTable(from, to, params, header) {
    const res = await axios.put(`https://project-for-fos-mld.herokuapp.com/api/changeTable/${from}/${to}`, params, header);

    return res.data;
  }

  async confirmPayment(orderId, params, header) {
    const res = await axios.put(`https://project-for-fos-mld.herokuapp.com/api/orders/confirm/${orderId}`, params, header);

    return res.data;
  }
}

export const employeeService = new EmployeeService();
