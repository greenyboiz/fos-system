import axios from 'axios';

class ClientService {
  async getListDish() {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/dishesClient');

    return res.data;
  }

  async getListTableStaff(header) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/tablesStaff', header);

    return res.data;
  }

  async getBestSellerDish(params, header) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/dashboard/bestseller', params, header);

    return res.data;
  }
}

export const clientService = new ClientService();
