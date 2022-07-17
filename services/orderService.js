import axios from 'axios';

class OrderService {
  async getOrderList() {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/orders');

    return res.data;
  }

  async postOrder(params) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/orders', params);

    return res.data;
  }

  async getOrderItem(orderItemId) {
    const res = await axios.get(`https://project-for-fos-mld.herokuapp.com/api/orderItem/order/${orderItemId}`);

    return res.data;
  }

  async postOrderItem(params) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/orderItem', params);

    return res;
  }

  async getTotalPayment(orderId) {
    const res =  await axios.get(`https://project-for-fos-mld.herokuapp.com/api/payment/${orderId}`);

    return res;
  }
}

export const orderService = new OrderService();
