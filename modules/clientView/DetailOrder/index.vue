<template>
  <div class="client-view">
    <div class="headline">
      <div class="headline__title">MLD Restaurent</div>
      <div class="headline__support">Support</div>
    </div>
    <div class="searchArea">
      <div class="searchBox d-flex align-items-center">
        <img
          class="searchBox__icon"
          src="~/assets/icons/search_gray_icon.png"
        />
        <input
          v-model="searchText"
          class="searchBox__input"
          placeholder="Tìm kiếm món ăn"
        />
        <div v-if="searchText" class="cursor-pointer" @click="removeKeyword()">
          <img
            class="searchBox__remove"
            src="~/assets/icons/remove_search.svg"
          />
        </div>
      </div>
    </div>
    <div class="special">
      <template v-if="isLoading">
        <div class="loading">
          <Loading />
        </div>
      </template>
      <template v-else>
        <div class="special__list">
          <div v-for="spec in orderList" :key="spec.id" class="special-item">
            <div class="spec-image">
              <img :src="spec.dishes.dishImage" style="border-radius: 8px" alt="" width="96px" height="96px">
            </div>
            <div class="spec-info">
              <div class="spec-name">{{ spec.dishes.dishesName }}</div>
              <div class="spec-price">
                <div class="spec-cost">{{ currencyFormatter(spec.dishes.salePrice) }} VNĐ</div>
                <div class="spec-remove" @click="handleRemoveOrderItem(spec.orderItemId)">
                  <Trash />
                </div>
              </div>
              <div class="spec-qua">
                <div class="spec-quantity">
                  <div class="changeNum">
                    <span class="downNumber" @click="decreaseDish(spec)"><i class="bi bi-dash-circle"></i></span>
                    <span>{{ spec.quantity }}</span>
                    <span class="upNumber" @click="increaseDish(spec)"><i class="bi bi-plus-circle"></i></span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </template>
    </div>
    <div class="totalOrder">
      <div class="payment-info">
        <div class="info-item">
          <div class="info-item_title">Giá:</div>
          <div class="info-item_number">{{ currencyFormatter(totalPriceOrder || 0) }} VNĐ</div>
        </div>
        <div class="info-item">
          <div class="info-item_title">VAT:</div>
          <div class="info-item_number">10%</div>
        </div>
        <div class="info-total">
          <div class="info-item_title">Tổng cộng:</div>
          <div class="info-item_number">{{ currencyFormatter((totalPriceOrder + (totalPriceOrder * 10) / 100) || 0) }} VNĐ</div>
        </div>
      </div>
      <div class="action-btn">
        <div v-if="!isOrder" class="btn-order" @click="handleDoneOrder()">
          <button>ORDER</button>
        </div>
        <div v-else class="btn-order" @click="handleOrderMore()">
          <nuxt-link to="/khach-hang/order">
            <button>Gọi thêm</button>
          </nuxt-link>
        </div>
        <div class="btn-payment" @click="handleGetReceipt()">
          <nuxt-link to="/khach-hang/hoa-don/:orderId?">
            <button>Thanh toán</button>
          </nuxt-link>
        </div>
      </div>
    </div>
    <SupportModal ref="supportModalRef" />
  </div>
</template>

<script>
import Trash from '@/components/CustomIcon/trash.vue';
import { mapState } from 'vuex';
import SupportModal from '@/components/common/SupportModal/index.vue';
import Loading from '@/components/common/Loading/index.vue';
import commonMixin from '@/plugins/commonMixin';
import { orderService } from '@/services';
import { map } from 'lodash';

export default {
  name: 'Page',

  components: {
    Trash,
    SupportModal,
    Loading,
  },

  mixins: [commonMixin],

  layout: 'default-no-header',

  data() {
    return {
      searchText: '',
      numberOfDishes: 0,
      orderList: [],
      totalPrice: 0,
      isOrder: false,
      isLoading: false,
    };
  },

  head() {
    return {
      title: 'Order Detail',
    };
  },

  computed: {
    ...mapState('clientView', {
      orderId: (state) => state.orderId,
      orderItemList: (state) => state.orderItemList,
    }),

    totalPriceOrder() {
      let total = 0;
      map(this.orderList, (item) => {
        total += item.quantity * item.price;
      });
      return total;
    }
  },

  mounted() {
    this.getOrderItemList();
  },

  methods: {
    handleShowSupportModal() {
      this.$refs.supportModalRef.show();
    },

    removeKeyword() {
      this.searchText = '';
    },

    decreaseDish(val) {
      if (val.quantity === 0) {
        return;
      }
      val.quantity--;
    },

    increaseDish(val) {
      val.quantity++;
    },

    async getOrderItemList() {
      this.isLoading = true;
      const orderIdTmp = localStorage.getItem('orderId');
      const res = await orderService.getOrderItem(orderIdTmp).finally(() => {
        this.isLoading = false;
      });

      if (res.success) {
        this.orderList = res.data;
      }
    },

    async handleRemoveOrderItem(id) {
      this.isLoading = true;
      const res = await orderService.deleteOrderItem(id, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      }).finally(() => {
        this.isLoading = false;
      });

      if (res) {
        this.getOrderItemList();
      }
    },

    handleDoneOrder() {
      this.isOrder = true;
      setTimeout(() => {
        // this.isLoading = false;
        this.isLoading = true;
      }, 1000);
      this.isLoading = false;
    },

    handleOrderMore() {
      const orderIdTmp = localStorage.getItem('orderId');
      this.$router.push({
        params: {
          orderId: orderIdTmp,
        }
      });
    },

    handleGetReceipt() {
      const orderIdTmp = localStorage.getItem('orderId');
      this.$router.push({
        params: {
          orderId: orderIdTmp,
        }
      });
    }

  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
