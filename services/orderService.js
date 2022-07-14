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
}

export const orderService = new OrderService();
