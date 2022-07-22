import axios from 'axios';

class TableManagementService {
  async getListTable(params, header) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/tables', params, header);

    return res;
  }

  async addTable(params, header) {
    const res = await axios.post('https://project-for-fos-mld.herokuapp.com/api/tables/add', params, header);

    return res;
  }

  async updateTable(params, header) {
    const res = await axios.put('https://project-for-fos-mld.herokuapp.com/api/tables/update', params, header);

    return res;
  }

  async deleteTable(tableId, header) {
    const res = await axios.delete(`https://project-for-fos-mld.herokuapp.com/api/tables/delete/${tableId}`, header);

    return res;
  }

  async getTableById(tableId, header) {
    const res = await axios.get(`https://project-for-fos-mld.herokuapp.com/api/tables/${tableId}`, header);

    return res.data;
  }

  async getQrCode(header) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/qrcode', header);

    return res.data;
  }
}

export const tableManagementService = new TableManagementService();
