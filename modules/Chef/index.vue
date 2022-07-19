<template>
  <div class="order__container">
    <div class="order__left">
      <h3 class="order__desc">Nhân viên Order</h3>

      <div class="order__table">
        <div class="table__head">
          <div class="table__col">Thực đơn</div>
          <div class="table__col">Thời gian</div>
          <div class="table__col">SL</div>
          <div class="table__col">Thao tác</div>
        </div>

        <div class="table__content">
          <div v-for="item in 20" :key="'o' + item" class="table__item">
            <div class="table__row">Pizza Hải sản</div>
            <div class="table__row time">
              <div>Bàn B3</div>
              <div class="btn__time">
                <img src="@/assets/icons/deadline.png" alt="" />
                <span>Vài giây trước</span>
              </div>
            </div>
            <div class="table__row">1</div>
            <div class="table__row btn">
              <div class="btn__cancel">
                <img src="@/assets/icons/cancel.png" alt="" />
              </div>
              <div class="btn__cancel">
                <img src="@/assets/icons/next.png" alt="" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="order__right">
      <h3 class="order__desc">Chế biến xong => chờ giao</h3>
      <div class="order__table">
        <div class="table__head">
          <div class="table__col">Thực đơn</div>
          <div class="table__col">Thời gian</div>
          <div class="table__col">SL</div>
          <div class="table__col">Thao tác</div>
        </div>

        <div class="table__content">
          <div v-for="item in 11" :key="'o' + item" class="table__item">
            <div class="table__row">Pizza Hải sản</div>
            <div class="table__row time">
              <div>Bàn B3</div>
              <div class="btn__time" @click="getOrderList()">
                <img src="@/assets/icons/deadline.png" alt="" />
                <span>Vài giây trước</span>
              </div>
            </div>
            <div class="table__row">1</div>
            <div class="table__row btn">
              <div class="btn__cancel">
                <img src="@/assets/icons/correct.png" alt="" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { size } from 'lodash';

import { orderService } from '@/services';
export default {
  name: 'OrderManagement',

  methods: {
    size,

    async getOrderList() {
      const res = await orderService.getOrderList();

      console.log(res);
    },
  },
};
</script>

<style lang="scss" scoped src="./styles.scss"></style>
