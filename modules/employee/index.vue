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

export default {
  name: 'Page',

  components: {
    Order,
    Dinner,
    SwapTableModal
  },

  data() {
    return {
      listDishes: [],
      isChoice: true,
      spOrderId: null,
      totalPriceOrder: 0,
      tableId: 1,
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

    async getPaymentByOrderId(orderId) {
      const res = await employeeService.getPaymentByOrderId(orderId, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        console.log(res);
      }
    },

    handleConfirmTransaction() {
      this.getPaymentByOrderId(1);
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
