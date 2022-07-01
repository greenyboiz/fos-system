import axios from 'axios';

class TableManagementService {
  async getListTable() {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/tables');

    return res;
  }

  async addTable(params) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/tables/add', params);

    return res;
  }

  async updateTable(params) {
    const res = await axios.put('https://project-for-fos-mld.herokuapp.com/api/tables/update', params);

    return res;
  }

  async deleteTable(tableId) {
    const res = await axios.delete(`https://project-for-fos-mld.herokuapp.com/api/tables/delete/${tableId}`);

    return res;
  }

  async getTableById(tableId) {
    const res = await axios.get(`https://project-for-fos-mld.herokuapp.com/api/tables/${tableId}`);

    return res.data;
  }

  async getQrCode() {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/qrcode');

    return res.data;
  }
}

export const tableManagementService = new TableManagementService();
