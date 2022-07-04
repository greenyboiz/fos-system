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
    <div class="dishDetail">
      <div class="dish-image">
        <img src="@/assets/images/cocktail.png" alt="" width="100%" />
      </div>
      <div class="dish-name">
        {{ dishDetail.dishesName }}
      </div>
      <div class="dish-review">
        <StarFill /> 5
      </div>
      <div class="dish-price">{{ dishDetail.salePrice }}</div>
      <div class="dish-desc">{{ dishDetail.description }}</div>
    </div>
    <div class="totalOrder">
      <button>ORDER</button>
    </div>
  </div>
</template>

<script>
import { menuManagementService } from '@/services';
import StarFill from '@/components/CustomIcon/star-fill.vue';
export default {
  name: 'Page',

  components: {
    StarFill,
  },

  layout: 'default-no-header',

  data() {
    return {
      searchText: '',
      dishDetail: {},
    };
  },

  head() {
    return {
      title: 'Dish Detail',
    };
  },

  computed: {
    totalOrder() {
      return 10;
    }
  },

  mounted() {
    this.getDishById();
  },

  methods: {
    removeKeyword() {
      this.searchText = '';
    },

    async getDishById() {
      const res = await menuManagementService.getDishById(1);

      if (res.success) {
        this.dishDetail = res.data;
      }
    },

    handleSelectCategory(val) {
      this.selectedCategory = val.categoryName;
    }
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
