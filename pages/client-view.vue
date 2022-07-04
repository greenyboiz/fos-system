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

<style lang="scss" scoped>
.client-view {
  padding: 12px;
  overflow: auto;
  width: 100%;
  height: 100vh;
  position: relative;
}

.headline {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;

  &__title {
    font-size: 20px;
    font-weight: 700;
  }

  &__support {
    padding: 8px;
    color: #fff;
    background: #069255;
    border-radius: 12px;
    font-size: 14px;
  }
}

.searchArea {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 1rem;

  &__counter {
    margin-right: 15px;
  }

  .searchBox {
    padding: 0px 10px;
    width: 100%;
    height: 36px;
    border: 1px solid #b7b7b7;
    border-radius: 30px;

    &__input {
      font-size: 14px;
      border: none;
      outline: none;
      width: 100%;
      margin-left: 5px;
      &::placeholder {
        color: #a3a1a1 !important;
      }
    }

    &__remove {
      width: 15px;
    }
  }
}

.campaign {
  margin-bottom: 2rem;
}

.menu {
  margin-bottom: 1rem;

  &__category {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    margin-bottom: 1rem;

    .cate-item, .cate-all {
      background: rgba(140, 140, 140, 0.13);
      color: rgba(172, 172, 172, 1);
      border-radius: 8px;
      padding: 4px 8px;
    }

    .active {
      color: #fff;
      background: #000;
    }
  }

  &__list {
    display: grid;
    grid-template-columns: auto auto;
    grid-gap: 12px;
    margin-bottom: 1rem;

    .menu-item {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      padding: 12px;
      box-shadow: 0px 32px 64px rgba(140, 140, 140, 0.13);
      border-radius: 16px;

      .dishes-image {
        width: 100%;
      }

      .dishes-price {
        font-weight: 500;
      }

      .dishes-orderNum {
        width: 100%;
        display: flex;
        justify-content: space-between;
      }
    }
  }

  &__paging {
    display: flex;
    align-content: center;
    justify-content: space-evenly;
  }
}

.special {
  margin-bottom: 1rem;

  &__head {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1rem;

    .head-title {
      font-size: 24px;
      font-weight: 500;
    }

    .head-seeAll {
      font-size: 14px;
      color: rgba(183, 183, 183, 1);
    }
  }

  &__list {
    margin-bottom: 1rem;

    .special-item {
      display: flex;
      box-shadow: 0px 32px 64px rgba(140, 140, 140, 0.13);
      border-radius: 12px;
      margin-bottom: 1rem;
      padding: 12px;

      .spec-image {
        margin-right: 0.5rem;
      }

      .spec-info {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        align-items: flex-start;
        width: 100%;

        .spec-price {
          font-size: 20px;
          font-weight: 500;
        }

        .spec-qua {
          display: flex;
          justify-content: space-between;
          width: 100%;

          .spec-quality {
            color: #EEC800;
          }
        }
      }
    }
  }
}

.totalOrder {
    position: fixed;
    bottom: 0;
    left: 50%;
    width: 100%;
    padding: 15px;
    transform: translateX(-50%);
    box-shadow: 0px 32px 64px rgba(140, 140, 140, 0.13);

    button {
      width: 100%;
      border: none;
      background: #069255;
      color: #fff;
      padding: 10px;
      font-size: 18px;
      border-radius: 8px;
    }
}
</style>
