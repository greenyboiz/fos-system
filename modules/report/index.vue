<template>
  <div class="report">
    <div class="report__wrapper container">
      <div class="top-report">
        <div class="column-chart">
          <div class="donut-top">DOANH THU</div>
          <div class="column-bot">
            <div class="column-left">
              <VueApexchart
                :key="revenueKey"
                type="bar"
                height="350"
                width="100%"
                :options="chartOptionsRV"
                :series="seriesRV"
              ></VueApexchart>
            </div>

            <!-- <div class="column-right">
              <div v-for="(item, idx) in listTotal" :key="'totel' + idx" class="total">
                <div class="total__money">{{ item.data }} VND</div>
                <div class="total__name">{{ item.name }}</div>
              </div>
            </div> -->
          </div>
        </div>

        <div class="donut-chart">
          <div class="donut-top">CHI PHÍ</div>
          <div class="donut-bot">
            <div>
              <VueApexchart
                :key="costKey"
                type="pie"
                width="100%"
                :options="chartOptionsC"
                :series="seriesC"
              ></VueApexchart>
            </div>
            <div class="legend">
              <div v-for="item in labelsC" :key="'box' + item" class="legend__item">
                <div class="legend__box"></div>
                {{ item }}
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="mid-report">
        <div class="bestSeller">
          <div class="bestSeller__header donut-top justify-content-between">
            <div class="bestSeller__title">TOP món ăn bán chạy</div>
            <div class="bestSeller__quantity">Lượng bán</div>
          </div>
          <div class="bestSeller__content">
            <div v-for="(bs, bsIndex) in bestSellerList" :key="bsIndex" class="content-item">
              <div class="content-name">
                <span class="content-index">{{ bsIndex + 1 }}</span>
                <span>{{ bs.dishesName }}</span>
              </div>
              <div class="content-quantity" style="padding-right: 20px">{{ bs.quantity }}</div>
            </div>
          </div>
        </div>
      </div>
      <div class="bottom-report">
        <div class="donut-top">LỢI NHUẬN</div>
        <VueApexchart
          :key="botkey"
          type="area"
          height="400"
          width="100%"
          :options="chartArea"
          :series="seriesArea"
        ></VueApexchart>
      </div>
      <div class="total-report">
        <div class="donut-top">Báo cáo</div>
        <table class="table table-bordered table-hover">
            <thead class="text-center">
              <tr>
                <th scope="col">STT</th>
                <th scope="col">Tháng</th>
                <th scope="col">Năm</th>
                <th scope="col">Doanh thu</th>
                <th scope="col">Chi phí</th>
                <th scope="col">Lợi nhuận</th>
              </tr>
            </thead>
            <tbody class="text-center">
                <tr v-for="(item, index) in dashBoardList" :key="index" class="tw-cursor-pointer">
                  <th scope="row">{{ `B${ index + 1 }` }}</th>
                  <td>{{ item.month }}</td>
                  <td>{{ item.year }}</td>
                  <td>{{ currencyFormatter(item.revenue) }}</td>
                  <td>{{ currencyFormatter(item.cost) }}</td>
                  <td>{{ currencyFormatter(item.profit) }}</td>
                </tr>
            </tbody>
          </table>
      </div>
    </div>
  </div>
</template>

<script>
import VueApexchart from 'vue-apexcharts';
import { chartService } from '@/services';
import { map, reduce } from 'lodash';
import commonMixin from '@/plugins/commonMixin';

export default {
  name: 'Report',

  components: {
    VueApexchart,
  },

  mixins: [commonMixin],

  data() {
    return {
      bestSellerList: [],
      dashBoardList: [],
      seriesRV: [
        {
          data: [],
        },
      ],
      chartOptionsRV: {
        chart: {
          id: 'basic-bar',
          toolbar: {
            show: false,
          },
        },
        plotOptions: {
          bar: {
            columnWidth: '45%',
            distributed: true,
          },
        },
        dataLabels: {
          enabled: false,
        },
        colors: ['#259062'],
        legend: {
          show: false,
        },
        xaxis: {
          categories: [],
        },
      },
      revenueKey: 0,
      botkey: 0,
      costKey: 0,
      seriesC: [],
      chartOptionsC: {
        chart: {
          type: 'pie',
        },
        legend: {
          show: false,
        },
        colors: ['#259062', '#EEBF5C', '#E9693D', '#D2494D', '#AAA1A1'],
        labels: [],
      },
      labelsC: [],

      seriesArea: [
        {
          name: '',
          data: [],
        },
      ],
      chartArea: {
        chart: {
          toolbar: {
            show: false,
          },
        },
        markers: {
          size: 5,
        },
        colors: ['#6FCF97'],
        dataLabels: {
          enabled: false,
        },
        grid: {
          strokeDashArray: 6,
        },
        stroke: {
          curve: 'smooth',
          width: 1.5,
        },
        xaxis: {
          categories: [],
        },
        legend: {
          horizontalAlign: 'left',
        },
      },
      listTotal: [],
    };
  },

  watch: {
    seriesRV: {
      handler() {
        this.revenueKey++;
        // this.getChartData();
        this.botkey++;
      },
      deep: true,
    },
  },

  mounted() {
    this.getChartData();
    this.getBestSellerDish();
  },

  methods: {
    async getChartData() {
      const res = await chartService.getChartData({
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        this.dashBoardList = res.data;
        const seriesData = map(res.data, (item) => {
          return item.revenue;
        });
        const xasisData = map(res.data, (item) => {
          return `${item.month}/${item.year}`;
        });
        const seriesCost = map(res.data, (item) => {
          return item.cost;
        });

        const seriesProfit = map(res.data, (item) => {
          return item.profit;
        });
        this.seriesRV[0].data = seriesData;
        this.chartOptionsRV.xaxis.categories = xasisData;
        this.seriesC = seriesCost;
        this.chartOptionsC.labels = xasisData;
        this.labelsC = xasisData;
        this.seriesArea[0].data = seriesProfit;
        this.chartArea.xaxis.categories = xasisData;

        const listTotal = [
          {
            data: reduce(
              seriesData,
              (sum, n) => {
                return sum + n;
              },
              0
            ),
            name: 'Doanh thu',
          },
          {
            data: reduce(
              seriesCost,
              (sum, n) => {
                return sum + n;
              },
              0
            ),
            name: 'Chi phí',
          },
          {
            data: reduce(
              seriesProfit,
              (sum, n) => {
                return sum + n;
              },
              0
            ),
            name: 'Lợi nhuận',
          },
        ];

        this.listTotal = listTotal;
      }
    },

    async getBestSellerDish() {
      const res = await chartService.getBestSellerDish({
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        this.bestSellerList = res.data;
        // const seriesData = map(res.data, (item) => {
        //   return item.quantity;
        // });
        // const xasisData = map(res.data, (item) => {
        //   return item.dishesName;
        // });
        // this.seriesC = seriesData;
        // this.chartOptionsC.labels = xasisData;
        // this.labelsC = xasisData;
      }
    }
  },
};
</script>

<style lang="scss" scoped src="./styles.scss"></style>
