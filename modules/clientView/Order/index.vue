<template>
  <div class="client-view">
    <div class="headline">
      <div class="headline__title">MLD Restaurent</div>
      <div class="headline__support" @click="handleShowSupportModal()">Support</div>
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
      <VueSlickCarousel :arrows="false" :dots="true" :autoplay="true">
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
        <div class="cate-all" :class="{ active: !catType }" @click="handleSelectAll()">Tất cả</div>
        <div v-for="(item) in listCategory" :key="item.categoryId" class="cate-item" :class="{ active: catType === item.categoryName }" @click="handleSelectCategory(item)">
          {{ item.categoryName }}
        </div>
      </div>
      <div class="menu__list">
        <div v-for="(val) in listDishes" :key="val.dishesId" class="menu-item" @click="handleOpenDetailDish(val)">
          <nuxt-link to="/khach-hang/chi-tiet-mon">
            <div class="dishes-image">
              <img :src="val.dishImage" alt="" width="165px" height="165px">
            </div>
            <div class="dishes-name">
              {{ val.dishesName }}
            </div>
            <div class="dishes-price">
              {{ currencyFormatter(val.salePrice) }} VNĐ
            </div>
          </nuxt-link>
            <div class="dishes-orderNum">
              <span style="font-weight: 500">SL:</span>
              <div class="changeNum">
                <span class="downNumber mr-1" @click="decreaseDish(val)"><i class="bi bi-dash-circle"></i></span>
                <input v-model="val.numberOfDish" type="number">
                <span class="upNumber ml-1" @click="increaseDish(val)"><i class="bi bi-plus-circle"></i></span>
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
            <div class="spec-price">{{ currencyFormatter(spec.salePrice) }} VNĐ</div>
            <div class="spec-qua">
              <div class="spec-quality">
                <StarFill :currentColor="'#EEC800'" />
                {{ spec.star }}
              </div>
              <div class="spec-quantity">
                <div class="changeNum">
                  <span class="downNumber" @click="decreaseDish()"><i class="bi bi-dash-circle"></i></span>
                  <span>{{ spec.numberOfDish }}</span>
                  <span class="upNumber" @click="increaseDish()"><i class="bi bi-plus-circle"></i></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="totalOrder" @click="handleOpenOrderList()">
      <nuxt-link to="/khach-hang/chi-tiet-order/:orderId?">
        <button>Bạn đã order ({{ totalOrder }})</button>
      </nuxt-link>
    </div>
    <SupportModal ref="supportModalRef" />
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
import VueSlickCarousel from 'vue-slick-carousel';
import { menuManagementService, orderService } from '@/services';
import StarFill from '@/components/CustomIcon/star-fill.vue';
import { map, filter, reduce } from 'lodash';
import SupportModal from '@/components/common/SupportModal/index.vue';
import commonMixin from '@/plugins/commonMixin';
export default {
  name: 'Page',

  components: {
    VueSlickCarousel,
    StarFill,
    SupportModal
  },

  mixins: [commonMixin],

  layout: 'default-no-header',

  data() {
    return {
      searchText: '',
      listDishes: [],
      totalDishes: [],
      listCategory: [],
      dishId: null,
      catType: '',
      numberOfDishes: 0,
      bestSeller: [
        { id: 1, name: 'Cocktail Bloody Marry', salePrice: 599999, star: 4.5, numberOfDish: 0 },
        { id: 2, name: 'Cocktail None Bloody Marry', salePrice: 599999, star: 4.5, numberOfDish: 0 },
        { id: 3, name: 'Cocktail Bloody None Marry', salePrice: 599999, star: 4.5, numberOfDish: 0 },
      ],
    };
  },

  head() {
    return {
      title: 'Client',
    };
  },

  computed: {
    ...mapState('clientView', {
      orderId: (state) => state.orderId,
      orderItemList: (state) => state.orderItemList,
    }),

    totalOrder() {
      const totalDish = map(this.listDishes, (item) => item.numberOfDish);
      const total = 0;
      const totalNum = reduce(totalDish, (pre, cur) => pre + cur, total);
      return totalNum;
    }
  },

  mounted() {
    this.getListCategory();
    this.getListDish();
    console.log(this.orderId);
  },

  methods: {
    ...mapMutations('clientView', {
      updateDishId: 'updateDishId',
      updateOrderId: 'updateOrderId',
      updateTotalPriceOrder: 'updateTotalPriceOrder',
      updateOrderItemList: 'updateOrderItemList',
    }),

    removeKeyword() {
      this.searchText = '';
    },

    handleShowSupportModal() {
      this.$refs.supportModalRef.show();
    },

    decreaseDish(val) {
      if (val.numberOfDish === 0) {
        return;
      }
      val.numberOfDish--;
    },

    increaseDish(val) {
      val.numberOfDish++;
      // this.getOrderItemList();
      // this.postOrderItem();
    },

    handleOpenDetailDish(val) {
      this.dishId = val.dishesId;
      this.updateDishId(val.dishesId);
    },

    handleSelectAll() {
      this.catType = '';
      this.getListDish();
    },

    async getListDish() {
      const res = await menuManagementService.getListDish();

      const dishesList = res.data;

      map(dishesList, (item) => {
        item.numberOfDish = 0;
        return item;
      });

      this.listDishes = dishesList;
      this.totalDishes = dishesList;
    },

    async getListCategory() {
      const res = await menuManagementService.getListCategory();

      if (res.success) {
        this.listCategory = res.data;
      }
    },

    async postOrderItem() {
      const test = [];
      const orderIdTmp = localStorage.getItem('orderId');
      filter(this.totalDishes, (item) => {
        if (item.numberOfDish > 0) {
          test.push({
            'orders': {
                'orderId': orderIdTmp
            },
            'dishes': {
                'dishesId': item.dishesId
            },
            'quantity': item.numberOfDish
          });
        }
      });
      const requestParam = {
        'list': test
      };

      const res = await orderService.postOrderItem(requestParam);

      if (res.success) {
        this.updateOrderId(orderIdTmp);
        console.log(orderIdTmp);
        this.getOrderItemList(orderIdTmp);
        this.getTotalPayment(orderIdTmp);
        this.$router.push({
          params: {
            orderId: orderIdTmp,
          }
        });
      }
    },

    handleOpenOrderList() {
      this.postOrderItem();
    },

    async getOrderItemList(orderId) {
      const res = await orderService.getOrderItem(orderId);

      if (res.success) {
        const orderList = res.data;
        this.updateOrderItemList(orderList);
      }
    },

    async getTotalPayment(orderId) {
      const res = await orderService.getTotalPayment(orderId);

      if (res.success) {
        this.updateTotalPriceOrder(res.data);
      }
    },

    handleSelectCategory(val) {
      this.catType = val.categoryName;
      const filterDish = filter(this.totalDishes, (item) => item.category.categoryName === this.catType);
      this.listDishes = filterDish;
    }
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
