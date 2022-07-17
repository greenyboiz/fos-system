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
      <div class="special__list">
        <div v-for="spec in orderItemList" :key="spec.id" class="special-item">
          <div class="spec-image">
            <img :src="spec.dishes.dishImage" alt="" width="96px">
          </div>
          <div class="spec-info">
            <div class="spec-name">{{ spec.dishes.dishesName }}</div>
            <div class="spec-price">
              <div class="spec-cost">{{ spec.dishes.salePrice }} VNĐ</div>
              <div class="spec-remove" @click="handleRemoveOrderItem()">
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
    </div>
    <div class="totalOrder">
      <div class="payment-info">
        <div class="info-item">
          <div class="info-item_title">Giá:</div>
          <div class="info-item_number">{{ totalPriceOrder || 0 }} VNĐ</div>
        </div>
        <div class="info-item">
          <div class="info-item_title">VAT:</div>
          <div class="info-item_number">10%</div>
        </div>
        <div class="info-total">
          <div class="info-item_title">Tổng cộng:</div>
          <div class="info-item_number">{{ (totalPriceOrder + (totalPriceOrder * 10) / 100) || 0 }} VNĐ</div>
        </div>
      </div>
      <div class="action-btn">
        <div class="btn-order">
          <button>ORDER</button>
        </div>
        <div class="btn-payment">
          <button>Thanh toán</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Trash from '@/components/CustomIcon/trash.vue';
import { mapState, mapMutations } from 'vuex';
export default {
  name: 'Page',

  components: {
    Trash,
  },

  layout: 'default-no-header',

  data() {
    return {
      searchText: '',
      numberOfDishes: 0,
      orderList: [],
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
      totalPriceOrder: (state) => state.totalPriceOrder,
      orderItemList: (state) => state.orderItemList,
    }),
  },

  mounted() {
    console.log(this.orderItemList);
  },

  methods: {
    ...mapMutations('clientView', {
      updateTotalPriceOrder: 'updateTotalPriceOrder',
    }),

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

    handleRemoveOrderItem() {

    }

  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
