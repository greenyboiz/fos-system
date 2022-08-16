<template>
  <div class="report">
    <div class="report__wrapper">
      <div class="top-report">
        <div class="column-chart">
          <VueApexchart
            :key="revenueKey"
            type="bar"
            height="350"
            width="50%"
            :options="chartOptionsRV"
            :series="seriesRV"
          ></VueApexchart>
        </div>
        <div class="donut-chart">
          <VueApexchart
            :key="revenueKey"
            type="pie"
            height="350"
            width="50%"
            :options="chartOptionsRV"
            :series="seriesRV"
          ></VueApexchart>
        </div>
      </div>
      <div class="mid-report"></div>
      <div class="bottom-report"></div>
    </div>
  </div>
</template>

<script>
import VueApexchart from 'vue-apexcharts';
import { chartService } from '@/services';
import { map } from 'lodash';
export default {
  name: 'Report',

  components: {
    VueApexchart,
  },

  data() {
    return {
      seriesRV: [{
        name: 'Revenue',
        data: []
      }],
      chartOptionsRV: {
        chart: {
          id: 'basic-bar'
        },
        xaxis: {
          categories: []
        }
      },
      revenueKey: 0,
    };
  },

  watch: {
    seriesRV: {
      handler() {
        this.revenueKey++;
        // this.getChartData();
      },
      deep: true,
    }
  },

  mounted() {
    this.getChartData();
  },

  methods: {
    async getChartData() {
      const res = await chartService.getChartData({
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        const seriesData = map(res.data, (item) => {
          return item.revenue;
        });
        const xasisData = map(res.data, (item) => {
          return `Tháng ${item.month}/${item.year}`;
        });
        this.seriesRV[0].data = seriesData;
        this.chartOptionsRV.xaxis.categories = xasisData;
      }
    },
  },
};
</script>

<style></style>
