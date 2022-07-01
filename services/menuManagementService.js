import axios from 'axios';

class MenuManagementService {
  async getListDish() {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/dishes');

    return res;
  }

  async addDish(params) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/dishes/add', params);

    return res;
  }

  async updateDish(params) {
    const res = await axios.put('https://project-for-fos-mld.herokuapp.com/api/dishes/update', params);

    return res;
  }

  async deleteDish(dishId) {
    const res = await axios.delete(`https://project-for-fos-mld.herokuapp.com/api/dishes/delete/${dishId}`);

    return res;
  }

  async getDishById(dishId) {
    const res = await axios.get(`https://project-for-fos-mld.herokuapp.com/api/dishes/${dishId}`);

    return res.data;
  }

  async getListCategory() {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/category');

    return res;
  }
}

export const menuManagementService = new MenuManagementService();
