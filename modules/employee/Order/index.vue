<template>
  <div class="order">
    <div class="order__top">
      <!-- <div class="order__option">
        <div
          v-for="item in listTypeOrder"
          :key="'menu' + item.categoryId"
          class="order__type"
          :class="{ active: currentIndex === item.categoryId }"
          @click="handleSelectType(item.categoryId)"
        >
          {{ item.categoryName }}
        </div>
         <li class="tabs-slider" role="presentation"></li> -->
      <!-- </div> -->
    </div>

    <!--  -->

    <div class="order__list">
      <div v-for="(item, index) in listDish" :key="'item' + index" class="order__i">
        <div class="order__item">
          <div class="order__img">
            <img :src="item.dishImage" />
          </div>

          <div class="order__info">
            <p class="order__name">{{ item.dishesName || '' }}</p>

            <div class="order__detail">
              <div class="order__price">{{ currencyFormatter(item.salePrice) || 0 }}đ</div>
              <!-- <div class="order__add">
                <img src="@/assets/images/plus.png" />
              </div> -->
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { uniqBy, filter } from 'lodash';

import { employeeService } from '@/services';
import commonMixin from '@/plugins/commonMixin';

export default {
  name: 'Order',

  mixins: [commonMixin],

  data() {
    return {
      listTypeOrder: [],

      currentIndex: 1,
      listDish: [],
      listFood: [],
      tableId: null,
    };
  },

  mounted() {
    this.getListDish();
  },

  methods: {
    handleSelectType(id) {
      this.currentIndex = id;

      this.listFood = filter(this.listDish, (item) => item.category.categoryId === id);
    },

    async getListDish() {
      const res = await employeeService.getDishes({
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });
      this.listDish = res.data;

      this.listTypeOrder = uniqBy(
        res.data.map((x) => x.category),
        'categoryId'
      );

      this.listFood = filter(this.listDish, (item) => item.category.categoryId === this.currentIndex);
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
