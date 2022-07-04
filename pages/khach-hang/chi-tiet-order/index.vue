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
        <div v-for="spec in bestSeller" :key="spec.id" class="special-item">
          <div class="spec-image">
            <img src="@/assets/images/menu-image.png" alt="" width="96px">
          </div>
          <div class="spec-info">
            <div class="spec-name">{{ spec.name }}</div>
            <div class="spec-price">
              <div class="spec-cost">{{ spec.salePrice }} VNĐ</div>
              <div class="spec-remove">
                <Trash />
              </div>
            </div>
            <div class="spec-qua">
              <div class="spec-quantity">
                <div class="changeNum">
                  <span class="downNumber" @click="decreaseDish()"><i class="bi bi-dash-circle"></i></span>
                  <span>{{ numberOfDishes }}</span>
                  <span class="upNumber" @click="increaseDish()"><i class="bi bi-plus-circle"></i></span>
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
          <div class="info-item_number">2.500.000 VNĐ</div>
        </div>
        <div class="info-item">
          <div class="info-item_title">VAT:</div>
          <div class="info-item_number">10%</div>
        </div>
        <div class="info-total">
          <div class="info-item_title">Tổng cộng:</div>
          <div class="info-item_number">2.750.000 VNĐ</div>
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
import { menuManagementService } from '@/services';
import Trash from '@/components/CustomIcon/trash.vue';
export default {
  name: 'Page',

  components: {
    Trash,
  },

  layout: 'default-no-header',

  data() {
    return {
      searchText: '',
      listDishes: [],
      listCategory: [],
      selectedCategory: '',
      numberOfDishes: 0,
      bestSeller: [
        { id: 1, name: 'Cocktail Bloody Marry', salePrice: 599999, star: 4.5 },
        { id: 2, name: 'Cocktail None Bloody Marry', salePrice: 599999, star: 4.5 },
        { id: 3, name: 'Cocktail Bloody None Marry', salePrice: 599999, star: 4.5 },
      ]
    };
  },

  head() {
    return {
      title: 'Order Detail',
    };
  },

  computed: {
    totalOrder() {
      return 10;
    }
  },

  mounted() {
    this.getListCategory();
    this.getListDish();
  },

  methods: {
    removeKeyword() {
      this.searchText = '';
    },

    decreaseDish() {
      if (this.numberOfDishes === 0) {
        return;
      }
      this.numberOfDishes--;
    },

    increaseDish() {
      this.numberOfDishes++;
    },

    async getListDish() {
      const res = await menuManagementService.getListDish();

      this.listDishes = res.data;
    },

    async getListCategory() {
      const res = await menuManagementService.getListCategory();

      if (res.status === 200) {
        this.listCategory = res.data;
      }
    },

    handleSelectCategory(val) {
      this.selectedCategory = val.categoryName;
    }
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
