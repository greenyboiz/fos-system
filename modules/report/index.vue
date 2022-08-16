<template>
  <div class="report">
    <div class="report__wrapper">
      <div class="top-report">
        <div class="column-chart d-flex">
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
            :key="costKey"
            type="pie"
            height="350"
            width="50%"
            :options="chartOptionsC"
            :series="seriesC"
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
      costKey: 0,
      seriesC: [44, 55, 13, 43, 22],
          chartOptionsC: {
            chart: {
              width: 380,
              type: 'pie',
            },
            labels: ['Team A', 'Team B', 'Team C', 'Team D', 'Team E'],
            responsive: [{
              breakpoint: 480,
              options: {
                chart: {
                  width: 200
                },
                legend: {
                  position: 'bottom'
                }
              }
            }]
          },
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
        const seriesCost = map(res.data, (item) => {
          return item.cost;
        });
        this.seriesRV[0].data = seriesData;
        this.chartOptionsRV.xaxis.categories = xasisData;
        this.seriesC = seriesCost;
      }
    },
  },
};
</script>

<style></style>
