import axios from 'axios';

class ChartService {
  async getChartData(params, header) {
    const res = await axios.get('https://project-for-fos-mld.herokuapp.com/api/dashboard', params, header);

    return res.data;
  }
}

export const chartService = new ChartService();
