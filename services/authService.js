import axios from 'axios';

class AuthService {
  async login(params) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/login', params);

    return res.data;
  }
}

export const authService = new AuthService();
