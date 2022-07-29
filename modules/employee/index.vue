<template>
  <div class="employee">
    <div class="contain__left">
      <!--  -->
      <div class="table__title">
        <div class="table__name">Tên món ăn</div>
        <div class="table__name">Số Lượng</div>
        <div class="table__name">Giá (đ)</div>
        <div class="table__name">Thành tiền (đ)</div>
      </div>

      <div class="test">
        <!--  -->
        <div class="test__content">
          <div v-for="item in listDishes" :key="'test' + item.orderItemId" class="table__row">
            <td>{{ item.dishes.dishesName }}</td>
            <td>{{ item.quantity }}</td>
            <td>{{ currencyFormatter(item.dishes.salePrice) }}</td>
            <td>{{ currencyFormatter(item.dishes.salePrice * item.quantity) }}</td>
          </div>
        </div>

        <!--  -->
        <div class="test__footer">
          <div class="payment__select">
            <div>
              <div v-for="type in payment" :key="type.id">
                <CustomCheckbox
                  v-model="selectedPayment"
                  class="mr-2"
                  shape="circle"
                  type="radio"
                  styleCheck="dot"
                  :keyValue="`${type.id}s`"
                  :inputValue="type.type"
                  :customLabel="true"
                >
                  <template slot="custom-label"> {{ type.name }} </template>
                </CustomCheckbox>
            </div>
            </div>
          </div>
          <div class="intoMoney">
            Tổng:
            <span> {{ currencyFormatter(totalPriceOrder) }} đ </span>
          </div>

          <div class="test__manipulation">
            <button class="common__btn" @click="handleOpenSwapTableModal()">
              <div class="common__img">
                <img src="@/assets/icons/move.png" />
              </div>
              Chuyển bàn
            </button>
            <button class="common__btn">
              <div class="common__img">
                <img src="@/assets/icons/cooking1.png" />
              </div>
              Bào chế biến
            </button>
            <button class="common__btn" @click="handleConfirmTransaction()">
              <div class="common__img">
                <img src="@/assets/icons/pay.png" />
              </div>
              Thanh toán
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="contain__right">
      <!--  -->
      <div class="contain__right--head">
        <button class="header__btn" :class="{ active: isChoice }" @click="handleGetChoice(true)">Bàn</button>
        <button class="header__btn" :class="{ active: !isChoice }" @click="handleGetChoice(false)">Thực đơn</button>
      </div>

      <!--  -->

      <div class="test">
        <div class="table__content" :class="{ active: !isChoice }">
          <Order v-if="!isChoice" />
          <Dinner v-else />
        </div>

        <!--  -->
        <div v-if="isChoice" class="common">
          <div class="note">
            <div class="note__box a"></div>
            <div class="note__name">Đã có</div>
          </div>
          <!-- <div class="note">
            <div class="note__box b"></div>
            <div class="note__name">Đang chọn</div>
          </div> -->
          <div class="note">
            <div class="note__box c"></div>
            <div class="note__name">Trống</div>
          </div>
        </div>
      </div>
    </div>
    <SwapTableModal ref="swapTableRef" />
  </div>
</template>

<script>
import { employeeService, orderService } from '@/services';
import { size, isNumber } from 'lodash';
import Order from '../../modules/employee/Order/index.vue';
import Dinner from '../../modules/employee/Dinner/index.vue';
import SwapTableModal from './modals/SwapTableModal/index.vue';
import CustomCheckbox from '@/components/common/CustomCheckbox/index.vue';


import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

Vue.use(VueToast, { position: 'top' });

export default {
  name: 'Page',

  components: {
    Order,
    Dinner,
    SwapTableModal,
    CustomCheckbox,
  },

  data() {
    return {
      listDishes: [],
      isChoice: true,
      spOrderId: null,
      totalPriceOrder: 0,
      tableId: 1,
      orderId: null,
      payment: [
        { id: 1, name: 'Thanh toán online', type: 'banking' },
        { id: 2, name: 'Thanh toán bằng tiền mặt', type: 'cash' },
      ],
      selectedPayment: '',
    };
  },

  created() {
    this.$root.$on('tableId', this.tableHaveOrder);
  },

  beforeDestroy() {
    this.$root.$off('tableId', this.tableHaveOrder);
  },

  mounted() {
    this.getOrderByTableId();
  },

  methods: {
    tableHaveOrder(val) {
      this.tableId = val;
      console.log(this.tableId);
      this.getOrderByTableId();
    },

    handleOpenSwapTableModal() {
      this.$refs.swapTableRef.show();
    },

    async getOrderByTableId() {
      const res = await employeeService.getOrderByTableId(this.tableId, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res && res.success) {
        this.getOrderItemList(res.data.orderId);
        this.getTotalPayment(res.data.orderId);
        this.orderId = res.data.orderId;
      } else {
        console.log('as');
      }
    },

    async getOrderItemList(orderId) {
      const res = await orderService.getOrderItem(orderId);

      if (res.success) {
        this.listDishes = res.data;
      }
    },

    async getTotalPayment(orderId) {
      const res = await orderService.getTotalPayment(orderId, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.status === 200) {
        this.totalPriceOrder = res.data;
      }
    },

    async confirmPayment() {
      if (!this.orderId) {
        return;
      }

      if (!this.selectedPayment) {
        Vue.$toast.error('Vui lòng chọn 1 phương thức thanh toán');
        return;
      }

      const reqParams = {
        'fosUserId': this.$auth.$state.user.user.id,
        'paymentType': this.selectedPayment
      };

      const res = await employeeService.confirmPayment(this.orderId, reqParams, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        Vue.$toast.success('Đơn hàng đã thanh toán thành công');
        location.reload();
      }
    },

    handleConfirmTransaction() {
      this.confirmPayment();
    },

    handleGetChoice(bol) {
      this.isChoice = bol;
    },

    currencyFormatter (num) {
      if (!num) return 0;
      if (Number.parseInt(num)<1) return 0;
      if (isNumber(num)) {
        return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
      } else {
        const parsedNumber = this.currencyParser(num);
        if (isNaN(parsedNumber)) {
          return 0;
        } else {
          let result = parsedNumber.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
          if(size(result) > 1 && result.charAt(0) === '0') {
            result = result.substring(1);
          }
          return result;
        }
      }
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
