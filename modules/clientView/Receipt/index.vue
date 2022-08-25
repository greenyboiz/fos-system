<template>
  <div class="receipt">
    <div class="receipt__header">MLD Restaurent</div>
    <div class="receipt__content">
      <div class="receipt__order">Hóa đơn #{{ orderNo }} của {{ customerName }}</div>
      <div class="receipt__time">{{ createAt }}</div>
      <div class="receipt__detail">
        <b-table
          id="my-table"
          :items="orderItemList"
          :fields="fields"
          responsive
        >
          <template #cell(stt)="data">
            {{ data.index + 1 }}
          </template>
          <template #cell(dishes[dishesName])="data">
            {{ data.item.dishes.dishesName }}
          </template>
          <template #cell(price)="data">
            {{ currencyFormatter(data.item.price) }}
          </template>
        </b-table>
        <!-- <div class="table__header">
          <div class="table__col col-1">STT</div>
          <div class="table__col col-4">Tên món</div>
          <div class="table__col col-2">Số lượng</div>
          <div class="table__col col-5">Đơn giá</div>
        </div>
        <div class="table__body">
          <div v-for="(item, index) in orderItemList" :key="index" class="table__item">
            <div class="table__row col-1">{{ index + 1 }}</div>
            <div class="table__row col-4">{{ item.dishes.dishesName }}</div>
            <div class="table__row col-2">{{ item.quantity }}</div>
            <div class="table__row col-5">{{ currencyFormatter(item.dishes.salePrice) }}</div>
          </div>
        </div> -->
        <div class="total__dishes">
          <div class="label">Tổng sổ món: </div>
          <div class="quantity">{{ dishCount }}</div>
        </div>
        <div class="total__price">
          <div class="label">Tổng tiền: </div>
          <div class="price">{{ currencyFormatter(totalPayment) }}</div>
        </div>
        <div class="vat">
          <div class="label">VAT: </div>
          <div class="percent">10%</div>
        </div>
        <div class="payment">
          <div class="label">Thanh toán</div>
          <div class="cash">{{ currencyFormatter(totalPayment + (totalPayment * 10 / 100)) }}</div>
        </div>
      </div>
    </div>
    <div class="receipt__bankInfo">
      <div class="STK">STK: 010595284</div>
      <div class="bank">ViB</div>
      <div class="bank-owner">Chủ TK: Nguyễn Đức Mạnh</div>
      <div class="text-center mb-2">
        <img src="~/assets/images/qr_pay.png" width="100px" height="100px" alt="">
      </div>
    </div>
    <div class="receipt__footer">
      <div class="thanks-label">Cảm ơn Quý khách và hẹn gặp lại!</div>
      <!-- <img src="~/assets/images/barcode.png" width="210px" height="48px" alt=""> -->
      <div class="other__payment" style="text-align: left; margin-top: 0.5rem">
        <span style="font-size: 14px">
          Note: Quý khách vui lòng giữ lại hóa đơn sau khi thanh toán để mang ra quầy kiểm tra
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import commonMixin from '~/plugins/commonMixin';
import { orderService } from '@/services';
import moment from 'moment';
export default {
  name: 'Receipt',

  mixins: [commonMixin],

  data() {
    return {
      orderItemList: [],
      totalPayment: 0,
      orderNo: 0,
      createAt: '',
      customerName: '',
      fields: [
        { key: 'stt', label: 'STT' },
        { key: 'dishes[dishesName]', label: 'Tên món' },
        { key: 'quantity', label: 'Số lượng' },
        { key: 'price', label: 'Đơn giá' },
      ],
    };
  },

  computed: {
    ...mapState('clientView', {
      orderId: (state) => state.orderId,
    }),
    dishCount() {
      return this.orderItemList.length;
    },
  },

  mounted() {
    this.orderNo = localStorage.getItem('orderId');
    this.customerName = localStorage.getItem('customerName');
    this.createAt = moment(localStorage.getItem('createAt')).format('HH:mm DD/MM/YYYY');
    this.getOrderItemList();

    window.onpopstate = function (e) { window.history.forward(1); };
  },

  methods: {
    async getOrderItemList() {
      const orderIdTmp = localStorage.getItem('orderId');
      const res = await orderService.getOrderItem(orderIdTmp);

      if (res.success) {
        const orderList = res.data;
        this.orderItemList = orderList;
        this.getTotalPayment();
      }
    },

    async getTotalPayment() {
      const orderIdTmp = localStorage.getItem('orderId');
      const res = await orderService.getTotalPayment(orderIdTmp);

      if (res.status === 200) {
        this.totalPayment = res.data;
      }
    },
  }
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
