import axios from 'axios';

class MenuManagementService {
  async getListDish(header) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/dishes', header);

    return res.data;
  }

  async addDish(params, header) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/dishes/add', params, header);

    return res.data;
  }

  async updateDish(params, header) {
    const res = await axios.put('https://project-for-fos-mld.herokuapp.com/api/dishes/update', params, header);

    return res.data;
  }

  async deleteDish(dishId, header) {
    const res = await axios.delete(`https://project-for-fos-mld.herokuapp.com/api/dishes/delete/${dishId}`, header);

    return res.data;
  }

  async getDishById(dishId) {
    const res = await axios.get(`https://project-for-fos-mld.herokuapp.com/api/dishes/${dishId}`);

    return res.data;
  }

  async getListCategory(header) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/category', header);

    return res.data;
  }

  async deleteCategory(categoryId, header) {
    const res = await axios.delete(`https://project-for-fos-mld.herokuapp.com/api/category/delete/${categoryId}`, header);

    return res.data;
  }

  async updateCategory(params, header) {
    const res = await axios.put('https://project-for-fos-mld.herokuapp.com/api/category/update', params, header);

    return res.data;
  }

  async addCategory(params, header) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/category/add', params, header);

    return res.data;
  }
}

export const menuManagementService = new MenuManagementService();
