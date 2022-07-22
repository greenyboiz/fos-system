import axios from 'axios';

class OrderService {
  async getOrderList(header) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/orders', header);

    return res.data;
  }

  async postOrder(params, header) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/orders', params, header);

    return res.data;
  }

  async getOrderItem(orderItemId, header) {
    const res = await axios.get(`https://project-for-fos-mld.herokuapp.com/api/orderItem/order/${orderItemId}`, header);

    return res.data;
  }

  async postOrderItem(params, header) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/orderItem', params, header);

    return res;
  }

  async getTotalPayment(orderId, header) {
    const res = await axios.get(`https://project-for-fos-mld.herokuapp.com/api/payment/${orderId}`, header);

    return res;
  }

  async deleteOrder(orderId, header) {
    const res = await axios.delete(`https://project-for-fos-mld.herokuapp.com/api/orders/delete/${orderId}`, header);

    return res;
  }
}

export const orderService = new OrderService();
