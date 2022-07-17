import axios from 'axios';

class EmployeeService {
  async getDishes() {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/dishes');

    return res.data;
  }

  async getTables() {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/tables');

    return res.data;
  }
}

export const employeeService = new EmployeeService();
