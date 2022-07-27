import axios from 'axios';

class MenuManagementService {
  async getListDish(header) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/dishes', header);

    return res;
  }

  async addDish(params, header) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/dishes/add', params, header);

    return res;
  }

  async updateDish(params, header) {
    const res = await axios.put('https://project-for-fos-mld.herokuapp.com/api/dishes/update', params, header);

    return res;
  }

  async deleteDish(dishId, header) {
    const res = await axios.delete(`https://project-for-fos-mld.herokuapp.com/api/dishes/delete/${dishId}`, header);

    return res;
  }

  async getDishById(dishId) {
    const res = await axios.get(`https://project-for-fos-mld.herokuapp.com/api/dishes/${dishId}`);

    return res.data;
  }

  async getListCategory(header) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/category', header);

    return res;
  }
}

export const menuManagementService = new MenuManagementService();
