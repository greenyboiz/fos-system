<template>
  <div class="menuManagement">
    <!-- Top -->
    <div class="headline">
      <div class="headline__top">
        <div class="headline__title">Quản lý đơn hàng</div>
      </div>

      <div class="headline__bottom">
        <span>Danh sách đơn hàng</span> -
        <span class="headline__count">{{ size(listOrder) }} đơn</span>
      </div>
    </div>

    <!-- Content -->

    <div class="main">
      <div class="table">
        <div class="table__wrapper">
          <template v-if="isLoading">
            <div class="text-center text-danger my-2">
              <div class="loading">
                <Loading />
              </div>
            </div>
          </template>
          <template v-else>
            <b-table
              id="my-table"
              :items="listOrder"
              :per-page="10"
              :current-page="currentPage"
              :fields="fields"
              bordered
              responsive
            >
              <template #cell(orderId)="data">
                <div v-if="currentPage > 1">
                  O{{
                    data.index + 1 === 10
                      ? `${currentPage}0`
                      : `${currentPage - 1}${data.index + 1}`
                  }}
                </div>
                <div v-else>O{{ data.index + 1 }}</div>
              </template>
              <template #cell(totalMoneyOfOrder)="data">
                {{ currencyFormatter(data.item.totalMoneyOfOrder) }}
              </template>
              <template #cell(submitTime)="data">
                {{ convertTime(data.item.submitDate) }}
              </template>
              <template #cell(action)="data">
                <div class="table__row align-items-center">
                  <div class="">
                    <button class="btn__edit" data-toggle="modal" data-target="#myModal" @click="editClick(data.item.orderId)">
                      Món ăn
                    </button>
                  </div>
                </div>
              </template>
            </b-table>
            <!-- Paging -->
            <section class="pagination__wrap">
              <div class="pagination__list">
                <b-pagination
                  v-model="currentPage"
                  :total-rows="totalOrder"
                  :per-page="10"
                  aria-controls="my-table"
                ></b-pagination>
              </div>
            </section>
          </template>
        </div>
      </div>
    </div>

    <OrderModal ref="detailOrder" />
  </div>
</template>

<script>
import { size } from 'lodash';
import { orderService } from '@/services';
import Loading from '@/components/common/Loading/index.vue';
import OrderModal from './modals/OrderModal/index.vue';
import commonMixin from '@/plugins/commonMixin';
import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';
import moment from 'moment';

Vue.use(VueToast, { position: 'top' });

export default {
  name: 'OrderManagement',

  components: {
    Loading,
    OrderModal,
  },

  mixins: [commonMixin],

  data() {
    return {
      searchText: '',
      listOrder: [],
      isLoading: false,
      currentPage: 1,
      fields: [
        { key: 'orderId', label: 'STT' },
        { key: 'fullName', label: 'Tên khách hàng' },
        { key: 'contact', label: 'Số điện thoại' },
        { key: 'submitTime', label: 'Thời gian tạo đơn' },
        { key: 'totalMoneyOfOrder', label: 'Tổng tiền (đã bao gồm VAT)' },
        { key: 'action', label: 'Action' },
      ],
    };
  },

  computed: {
    totalOrder() {
      return this.listOrder.length;
    },
  },

  mounted() {
    this.getOrderList();
  },

  methods: {
    size,
    editClick(id) {
      this.$refs.detailOrder.show(id);
    },

    async getOrderList() {
      this.isLoading = true;
      const res = await orderService
        .getOrderList({
          headers: {
            Authorization: this.$auth.$storage._state['_token.local'],
          },
        })
        .finally(() => {
          this.isLoading = false;
        });

      if (res.success) {
        this.listOrder = res.data;
      }
    },

    removeOrder(id) {
      this.$confirmPopup.open({
        title: 'Xác nhận',
        message: 'Bạn có chắc muốn xóa đơn hàng này không?',
        confirmText: 'Xóa',

        confirmed: async () => {
          const res = await orderService.deleteOrder(id, {
            headers: {
              Authorization: this.$auth.$storage._state['_token.local'],
            },
          });

          if (res.success) {
            this.getOrderList();
            Vue.$toast.success('Xóa đơn hàng thành công');
          }
        },
      });
    },

    convertTime(time) {
      if (time) {
        return moment(time).format('HH:mm DD/MM/YYYY');
      }
      return '';
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
