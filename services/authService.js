import axios from 'axios';

class AuthService {
  async login(params) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/login', params);

    return res.data;
  }

  async getNewPassword(params) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/forgetPassword', params);

    return res.data;
  }

  async changePassword(params, header) {
    const res = await axios.put('https://project-for-fos-mld.herokuapp.com/api/users/changePassword', params, header);

    return res.data;
  }
}

export const authService = new AuthService();
