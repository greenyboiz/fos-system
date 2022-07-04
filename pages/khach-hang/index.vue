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
    <div class="campaign">
      <VueSlickCarousel :arrows="false" :dots="true">
        <div class="campaign-item">
          <img src="@/assets/images/campaign.jpg" alt="" width="100%" />
        </div>
        <div class="campaign-item">
          <img src="@/assets/images/campaign.jpg" alt="" width="100%" />
        </div>
      </VueSlickCarousel>
    </div>
    <div class="menu">
      <div class="menu__category">
        <div class="cate-all">Tất cả</div>
        <div v-for="(item) in listCategory" :key="item.categoryId" class="cate-item" @click="handleSelectCategory(item)">
          {{ item.categoryName }}
        </div>
      </div>
      <div class="menu__list">
        <div v-for="(val) in listDishes" :key="val.dishesId" class="menu-item">
          <div class="dishes-image">
            <img src="@/assets/images/menu-image.png" alt="" width="100%">
          </div>
          <div class="dishes-name">
            {{ val.dishesName }}
          </div>
          <div class="dishes-price">
            {{ val.salePrice }} VNĐ
          </div>
          <div class="dishes-orderNum">
            <span style="font-weight: 500">SL:</span>
            <div class="changeNum">
              <span class="downNumber" @click="decreaseDish()"><i class="bi bi-dash-circle"></i></span>
              <span>{{ numberOfDishes }}</span>
              <span class="upNumber" @click="increaseDish()"><i class="bi bi-plus-circle"></i></span>
            </div>
          </div>
        </div>
      </div>
      <div class="menu__paging">
        <i class="bi bi-chevron-left"></i>
        <span>1</span>
        <i class="bi bi-chevron-right"></i>
      </div>
    </div>
    <div class="special">
      <div class="special__head">
        <div class="head-title">Đặc biệt</div>
        <div class="head-seeAll">Xem tất cả</div>
      </div>
      <div class="special__list">
        <div v-for="spec in bestSeller" :key="spec.id" class="special-item">
          <div class="spec-image">
            <img src="@/assets/images/menu-image.png" alt="" width="96px">
          </div>
          <div class="spec-info">
            <div class="spec-name">{{ spec.name }}</div>
            <div class="spec-price">{{ spec.salePrice }} VNĐ</div>
            <div class="spec-qua">
              <div class="spec-quality">
                <StarFill :currentColor="'#EEC800'" />
                {{ spec.star }}
              </div>
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
      <button>Bạn đã order ({{ totalOrder }})</button>
    </div>
  </div>
</template>

<script>
import VueSlickCarousel from 'vue-slick-carousel';
import { menuManagementService } from '@/services';
import StarFill from '@/components/CustomIcon/star-fill.vue';
export default {
  name: 'Page',

  components: {
    VueSlickCarousel,
    StarFill
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
      title: 'Client',
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
