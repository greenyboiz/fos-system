import axios from 'axios';

class EmployeeService {
  async getDishes(header) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/dishes', header);

    return res.data;
  }

  async getTables(header) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/tables', header);

    return res.data;
  }
}

export const employeeService = new EmployeeService();
