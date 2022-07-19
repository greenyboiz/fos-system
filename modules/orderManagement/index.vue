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
            <div class="table__col">Địa chỉ</div>
            <div class="table__col">Đánh giá</div>
            <div class="table__col">Thao tác</div>
          </div>

          <tbody class="table__bot">
            <template v-if="isLoading">
              <div class="loading">
                <Loading />
              </div>
            </template>

            <template v-else>
              <div v-for="item in listOrder" :key="item.id" class="table__body">
                <div class="table__row">{{ item.orderId }}</div>
                <div class="table__row">{{ item.customer.firstName }} {{ item.customer.lastName }}</div>
                <div class="table__row">{{ item.customer.contact }}</div>
                <div class="table__row">{{ item.customer.address }}</div>
                <div class="table__row">{{ item.description }}</div>

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
                    <button class="btn__delete" @click="removeOrder(item.orderId)">
                      <img src="@/assets/icons/delete.png" alt="" />
                    </button>
                  </div>
                </div>
              </div>
            </template>
          </tbody>
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

export default {
  name: 'OrderManagement',

  components: {
    Loading,
    OrderModal,
  },

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

    remove(val) {
      this.deleteDish(val);
    },

    handleShowAddDishesModal() {
      this.$refs.addDishModalRef.show();
    },

    addedDish(val) {
      if (val) {
        this.getListDish();
      }
    },

    updatedDish(val) {
      if (val) {
        this.getListDish();
      }
    },

    async getOrderList() {
      this.isLoading = true;
      const res = await orderService.getOrderList().finally(() => {
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
          const res = await orderService.deleteOrder(id);

          if (res) {
            this.getOrderList();
          }
        },
      });
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
>
