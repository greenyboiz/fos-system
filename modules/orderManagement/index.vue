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
        <table class="table__wrapper">
          <div class="table__head">
            <div class="table__col">Mã ĐH</div>
            <div class="table__col">Tên khách hàng</div>
            <div class="table__col">Số điện thoại</div>
            <div class="table__col">Thời gian tạo đơn</div>
            <div class="table__col">Tổng tiền (đ)</div>
            <div class="table__col">Thao tác</div>
          </div>

          <div class="table__bot">
            <template v-if="isLoading">
              <div class="loading">
                <Loading />
              </div>
            </template>

            <template v-else>
              <div v-for="(item, index) in listOrder" :key="item.orderId" class="table__body">
                <div class="table__row">{{ `O${ index + 1 }` }}</div>
                <div class="table__row">{{ item.fullName }}</div>
                <div class="table__row">{{ item.contact }}</div>
                <div class="table__row">{{ item.submitTime }} {{ item.submitDate }}</div>
                <div class="table__row">{{ currencyFormatter(item.totalMoneyOfOrder) }}</div>

                <div class="table__row align-items-center">
                  <div class="btn-group align-top">
                    <button
                      class="btn__edit"
                      data-toggle="modal"
                      data-target="#myModal"
                      @click="editClick(item.orderId)"
                    >
                      Món ăn
                    </button>
                    <!-- <button class="btn__delete" @click="removeOrder(item.orderId)">
                      <img src="@/assets/icons/delete.png" alt="" />
                    </button> -->
                  </div>
                </div>
              </div>
            </template>
          </div>
        </table>
      </div>

      <!-- Paging -->
      <div class="pagination">
        <section class="pagination__wrap">
          <div class="pagination__list">
            <button class="pagination__button active">1</button>
            <button class="pagination__button">2</button>
          </div>
        </section>
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
    };
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
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
>
