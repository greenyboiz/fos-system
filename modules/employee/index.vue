<template>
  <div class="employee">
    <div class="contain__left">
      <!--  -->
      <div class="table__title">
        <div class="table__name">Tên món ăn</div>
        <div class="table__name">Số Lượng</div>
        <div class="table__name">Giá</div>
        <div class="table__name">Thành tiền</div>
      </div>

      <div class="test">
        <!--  -->
        <div class="test__content">
          <div v-for="item in listDishes" :key="'test' + item.dishesId" class="table__row">
            <td>{{ item.dishesName }}</td>
            <td>{{ item.discount }}</td>
            <td>{{ item.salePrice }}</td>
            <td>{{ item.costPrice }}</td>
          </div>
        </div>

        <!--  -->
        <div class="test__footer">
          <div class="intoMoney">
            Tổng:
            <span> {{ totalMoney }} </span>
          </div>

          <div class="test__manipulation">
            <button class="common__btn">
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
            <button class="common__btn">
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
          <div class="note">
            <div class="note__box b"></div>
            <div class="note__name">Đang chọn</div>
          </div>
          <div class="note">
            <div class="note__box c"></div>
            <div class="note__name">Trống</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { employeeService } from '@/services';

import Order from './Order/index.vue';
import Dinner from './Dinner/index.vue';

export default {
  name: 'Page',

  components: {
    Order,
    Dinner,
  },

  data() {
    return {
      listDishes: [],
      isChoice: true,
    };
  },

  head() {
    return {
      title: 'Dish Detail',
    };
  },

  computed: {
    totalMoney() {
      if (this.listDishes.length > 0) {
        const total = this.listDishes.reduce((accumulator, object) => {
          return accumulator + object.costPrice;
        }, 0);

        return total;
      }

      return 0;
    },
  },

  mounted() {
    this.getListDishes();
  },

  methods: {
    async getListDishes() {
      const res = await employeeService.getDishes();

      console.log(res);
      if (res.length > 0) {
        this.listDishes = res;
      }
    },

    handleGetChoice(bol) {
      this.isChoice = bol;
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
