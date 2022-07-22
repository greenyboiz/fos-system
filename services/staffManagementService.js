import axios from 'axios';

class StaffManagementService {
  async getListUser(params) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/users', params);

    return res.data;
  }

  async addUser(params, header) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/users/add', params, header);

    return res;
  }

  async updateUser(params, header) {
    const res = await axios.put('https://project-for-fos-mld.herokuapp.com/api/users/update', params, header);

    return res;
  }

  async deleteUser(userId, header) {
    const res = await axios.delete(`https://project-for-fos-mld.herokuapp.com/api/users/delete/${userId}`, header);

    return res;
  }

  async getUserById(userId, header) {
    const res = await axios.get(`https://project-for-fos-mld.herokuapp.com/api/users/${userId}`, header);

    return res.data;
  }
}

export const staffManagementService = new StaffManagementService();
