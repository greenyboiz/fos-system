<template>
  <div class="report">
    <div class="report__wrapper container">
      <div class="report__header">
        <div class="header-item bg-danger">
          Tổng order theo ngày: {{ totalOrderByDay }}
        </div>
        <div class="header-item bg-success">
          Chi phí theo tháng: {{ currencyFormatter(costByMonth) }}đ
        </div>
        <div class="header-item bg-info">
          Lợi nhuận theo tháng: {{ currencyFormatter(profitByMonth) }}đ
        </div>
      </div>
      <div class="top-report">
        <div class="column-chart">
          <div class="donut-top">
            <div class="top-label">
              DOANH THU
            </div>
            <div class="filterYear">
              <MultipleSelect
                v-model.number="yearRevenue"
                placeholder="Chọn năm"
                :options="listYearChart"
                :multiple="false"
                @input="handleSelectYearRevenue"
              />
            </div>
          </div>
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
          <div class="donut-top">
            CHI PHÍ
            <span class="ml-1" style="font-size: 14px">
              (5 tháng gần nhất)</span
            >
          </div>
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
              <div
                v-for="item in labelsC"
                :key="'box' + item"
                class="legend__item"
              >
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
            <div
              v-for="(bs, bsIndex) in bestSellerList"
              :key="bsIndex"
              class="content-item"
            >
              <div class="content-name">
                <span class="content-index">{{ bsIndex + 1 }}</span>
                <span>{{ bs.dishesName }}</span>
              </div>
              <div class="content-quantity" style="padding-right: 20px">
                {{ bs.quantity }}
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="bottom-report">
        <div class="donut-top">
          <div class="top-label">
            LỢI NHUẬN
          </div>
          <div class="filterYear">
            <MultipleSelect
              v-model.number="yearProfit"
              placeholder="Chọn năm"
              :options="listYearChart"
              :multiple="false"
              @input="handleSelectYearProfit"
            />
          </div>
        </div>
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
        <div class="donut-top">
          <div class="top-label">
            Báo cáo
          </div>
          <div class="filterYear">
            <MultipleSelect
              v-model.number="selectedYear"
              placeholder="Chọn năm"
              :options="listYear"
              :multiple="false"
              @input="handleSelectYear"
            />
          </div>
        </div>
        <b-table
          id="my-table"
          :items="filterDashboard"
          :per-page="10"
          :current-page="currentPage"
          :fields="fields"
          bordered
          responsive
        >
          <template #cell(stt)="data">
            B{{ data.index + 1 }}
          </template>
          <template #cell(revenue)="data">
            {{ currencyFormatter(data.item.revenue) }}
          </template>
          <template #cell(cost)="data">
            {{ currencyFormatter(data.item.cost) }}
          </template>
          <template #cell(profit)="data">
            {{ currencyFormatter(data.item.profit) }}
          </template>
        </b-table>
        <!-- Paging -->
        <section class="pagination__wrap">
          <div class="pagination__list">
            <b-pagination
              v-model="currentPage"
              :total-rows="totalOrderLength"
              :per-page="10"
              aria-controls="my-table"
            ></b-pagination>
          </div>
        </section>
      </div>
    </div>
  </div>
</template>

<script>
import VueApexchart from 'vue-apexcharts';
import MultipleSelect from 'vue-multiselect';
import { chartService, orderService, menuManagementService } from '@/services';
import { map, reduce, filter, reverse } from 'lodash';
import commonMixin from '@/plugins/commonMixin';

export default {
  name: 'Report',

  components: {
    VueApexchart,
    MultipleSelect
  },

  mixins: [commonMixin],

  data() {
    return {
      bestSellerList: [],
      dashBoardList: [],
      filterDashboard: [],
      cateList: [],
      orderByDay: [],
      totalOrder: [],
      currentPage: 1,
      profitByMonth: 0,
      costByMonth: 0,
      selectedYear: '2022',
      yearRevenue: new Date().getFullYear(),
      yearProfit: new Date().getFullYear(),
      listYear: ['Tất cả', 2022, 2023, 2024, 2025, 2026, 2027],
      listYearChart: [2022, 2023, 2024, 2025, 2026, 2027],
      fields: [
        { key: 'stt', label: 'STT' },
        { key: 'month', label: 'Tháng' },
        { key: 'year', label: 'Năm' },
        { key: 'revenue', label: 'Doanh thu' },
        { key: 'cost', label: 'Chi phí' },
        { key: 'profit', label: 'Lợi nhuận' },
      ],
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
        dataLabels: {
          enabled: true,
          formatter(val) {
            if (val < 10) {
              return '';
            }
            return `${Math.round(val * 100) / 100}%`;
          },
        },
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
    };
  },

  computed: {
    totalOrderByDay() {
      return this.orderByDay.length;
    },

    totalOrderLength() {
      return this.dashBoardList.length;
    },
  },

  watch: {
    seriesRV: {
      handler() {
        this.revenueKey++;
      },
      deep: true,
    },
    seriesArea: {
      handler() {
        this.botkey++;
      },
      deep: true,
    }
  },

  mounted() {
    this.getChartData();
    this.getBestSellerDish();
    this.getListOrder();
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
        this.filterDashboard = res.data;
        const seriesData = filter(res.data, (item) => item.year === this.yearRevenue);
        const xasisData = map(res.data, (item) => {
          return `${item.month}/${item.year}`;
        });

        this.seriesRV[0].data = map(seriesData, (item) => item.revenue);
        this.chartOptionsRV.xaxis.categories = map(seriesData, (item) => `${item.month}/${item.year}`);

        const seriesCost = map(res.data, (item) => {
          return item.cost;
        });

        if (seriesCost.length > 5) {
          this.seriesC = seriesCost.slice(seriesCost.length - 5);
        } else {
          this.seriesC = seriesCost;
        }
        if (xasisData.length > 5) {
          this.labelsC = xasisData.slice(xasisData.length - 5);
        } else {
          this.labelsC = xasisData;
        }
        this.chartOptionsC.labels = xasisData;
        this.costByMonth = reduce(seriesCost, (sum, n) => {
          return sum + n;
        });

        const seriesProfit = map(res.data, (item) => {
          return item.profit;
        });
        const chartAreaData = filter(res.data, (item) => item.year === this.yearProfit);
        this.seriesArea[0].data = map(chartAreaData, (item) => item.profit);
        this.chartArea.xaxis.categories = map(chartAreaData, (item) => `${item.month}/${item.year}`);

        this.profitByMonth = reduce(seriesProfit, (sum, n) => {
          return sum + n;
        });
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
      }
    },

    async getListOrder() {
      const res = await orderService.getOrderList({
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        const resData = res.data;
        const filterData = filter(resData, (item) => {
          const today = new Date();
          const dateTest = new Date(item.submitDate);
          if (today.getDate() === dateTest.getDate()) {
            return item;
          }
        });
        this.orderByDay = filterData;
      }
    },

    handleSelectYear(val) {
      const reverseArr = reverse(this.dashBoardList);
      if (val === 'Tất cả') {
        this.filterDashboard = reverseArr;
        return;
      }
      this.filterDashboard = filter(reverseArr, (item) => item.year === val);
    },

    handleSelectYearRevenue(val) {
      const seriesRV = filter(this.dashBoardList, (item) => item.year === val);
      this.seriesRV[0].data = map(seriesRV, (item) => item.revenue);
      this.chartOptionsRV.xaxis.categories = map(seriesRV, (item) => `${item.month}/${item.year}`);
    },

    handleSelectYearProfit(val) {
      const chartAreaData = filter(this.dashBoardList, (item) => item.year === val);
      this.seriesArea[0].data = map(chartAreaData, (item) => item.profit);
      this.chartArea.xaxis.categories = map(chartAreaData, (item) => `${item.month}/${item.year}`);
    }

    // async getListDish() {
    //   const res = await menuManagementService.getListDish();

    //   if (res.success) {
    //     const
    //   }
    // }
  },
};
</script>

<style lang="scss" scoped src="./styles.scss"></style>
