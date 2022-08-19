import axios from 'axios';
import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

Vue.use(VueToast, { position: 'top' });

class StaffManagementService {
  async getListUser(params) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/users', params);

    return res.data;
  }

  async addUser(params, header) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/users/add', params, header);

    return res.data;
  }

  async updateUser(params, header) {
    const res = await axios.put('https://project-for-fos-mld.herokuapp.com/api/users/update', params, header);

    return res.data;
  }

  async deleteUser(userId, header) {
    const res = await axios.delete(`https://project-for-fos-mld.herokuapp.com/api/users/delete/${userId}`, header);

    return res.data;
  }

  async getUserById(userId, header) {
    const res = await axios.get(`https://project-for-fos-mld.herokuapp.com/api/users/${userId}`, header);

    return res.data;
  }
}

export const staffManagementService = new StaffManagementService();
