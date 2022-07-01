import axios from 'axios';

class StaffManagementService {
  async getListUser() {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/users');

    return res;
  }

  async addUser(params) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/users/add', params);

    return res;
  }

  async updateUser(params) {
    const res = await axios.put('https://project-for-fos-mld.herokuapp.com/api/users/update', params);

    return res;
  }

  async deleteUser(userId) {
    const res = await axios.delete(`https://project-for-fos-mld.herokuapp.com/api/users/delete/${userId}`);

    return res;
  }

  async getUserById(userId) {
    const res = await axios.get(`https://project-for-fos-mld.herokuapp.com/api/users/${userId}`);

    return res.data;
  }
}

export const staffManagementService = new StaffManagementService();
