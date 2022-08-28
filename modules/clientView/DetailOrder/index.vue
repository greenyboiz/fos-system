<template>
  <div class="client-view">
    <b-container>
    <div ref="headline" class="headline">
      <div class="headline__title">Octavian Winery</div>
      <div class="headline__support" @click="handleShowSupportModal()">Support</div>
    </div>
    <div ref="searchArea" class="searchArea">
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
    <div class="special" :style="orderHeight">
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
                  SL: {{ spec.quantity }}
                  <!-- <div class="changeNum">
                    <span class="downNumber" @click="decreaseDish(spec)"><i class="bi bi-dash-circle"></i></span>
                    <span>{{ spec.quantity }}</span>
                    <span class="upNumber" @click="increaseDish(spec)"><i class="bi bi-plus-circle"></i></span>
                  </div> -->
                </div>
              </div>
            </div>
          </div>
        </div>
      </template>
    </div>
    <div ref="priceOrder" class="totalOrder">
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
        <!-- <div v-if="!isOrder" class="btn-order" @click="handleDoneOrder()">
          <button>ORDER</button>
        </div> -->
        <div class="btn-order" @click="handleOrderMore()">
          <nuxt-link :to="`/khach-hang/order/${orderIdLocal}`">
            <button>Gọi thêm</button>
          </nuxt-link>
        </div>
        <div class="btn-payment" @click="handleGetReceipt()">
          <nuxt-link :to="`/khach-hang/hoa-don/${orderIdLocal}`">
            <button>Thanh toán</button>
          </nuxt-link>
        </div>
      </div>
    </div>
    <SupportModal ref="supportModalRef" />
    </b-container>
  </div>
</template>

<script>
import Trash from '@/components/CustomIcon/trash.vue';
import SupportModal from '@/components/common/SupportModal/index.vue';
import Loading from '@/components/common/Loading/index.vue';
import commonMixin from '@/plugins/commonMixin';
import { orderService } from '@/services';
import { map } from 'lodash';
import SockJs from 'sockjs-client';
import StompClient from 'webstomp-client';

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
      orderIdLocal: null,
      orderHeight: {},
    };
  },

  head() {
    return {
      title: 'Order Detail',
    };
  },

  computed: {
    totalPriceOrder() {
      let total = 0;
      map(this.orderList, (item) => {
        total += item.quantity * item.price;
      });
      return total;
    }
  },

  watch: {
    orderList(newVal) {
      this.orderList = newVal;
    }
  },

  beforeUpdate() {
    this.matchHeight();
  },

  mounted() {
    this.orderIdLocal = localStorage.getItem('orderId');
    this.connect();
    this.getOrderItemList(this.orderIdLocal);
  },

  methods: {
    matchHeight() {
      const infoHeight = this.$refs.priceOrder.clientHeight;
      const headlineHeight = this.$refs.headline.clientHeight;
      const searchArea = this.$refs.searchArea.clientHeight;
      this.$set(this.orderHeight, 'max-height', `calc(100% - ${headlineHeight}px - ${searchArea}px - 2.2rem - ${infoHeight}px)`);
    },

    connect() {
      this.socket = new SockJs('https://project-for-fos-mld.herokuapp.com/ws');
      this.stompClient = StompClient.over(this.socket);
      this.stompClient.connect({}, this.onConnected);
    },

    onConnected() {
      this.stompClient.subscribe('/topic/staffRoom', this.onMessageReceived);
      const tableIdTmp = localStorage.getItem('tableId');

      this.sendMessage(tableIdTmp);
    },

    sendMessage(id) {
      if (this.stompClient) {
        const tables = {
          tableId: id,
        };
        this.stompClient.send('/app/chat.sendMessage', JSON.stringify(tables), {});
      }
    },

    onMessageReceived(payload) {
      const message = JSON.parse(payload.body);

      if(message.type === 'CHAT') {
          const element = document.getElementById(message.content);
          element.style.backgroundColor = '#00FF00';
      }
    },

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

    async getOrderItemList(orderId) {
      this.isLoading = true;
      // const orderIdTmp = localStorage.getItem('orderId');
      const res = await orderService.getOrderItem(orderId).finally(() => {
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

      if (res.success) {
        this.connect();
        this.getOrderItemList(this.orderIdLocal);
      }
    },

    handleDoneOrder() {
      this.isOrder = true;
      setTimeout(() => {
        // this.isLoading = false;
        this.getOrderItemList();
      }, 1500);
      // this.isLoading = false;
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
