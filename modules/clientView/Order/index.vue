<template>
  <div class="client-view">
    <b-container>
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
        <b-col v-for="(val) in listDishSearch" :key="val.dishesId" cols="6" class="menu-item" @click="handleOpenDetailDish(val)">
          <nuxt-link to="/khach-hang/chi-tiet-mon">
            <div class="dishes-image">
              <img :src="val.dishImage" class="img-thumbnail rounded mx-auto d-block" alt="">
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
        </b-col>
      </div>
      <!-- <div class="menu__paging">
        <i class="bi bi-chevron-left"></i>
        <span>1</span>
        <i class="bi bi-chevron-right"></i>
      </div> -->
    </div>
    <div class="special">
      <div class="special__head">
        <div class="head-title">Đặc biệt</div>
        <div class="head-seeAll">Xem tất cả</div>
      </div>
      <div class="special__list">
        <div v-for="(spec, specIndex) in bestSeller" :key="specIndex" class="special-item">
          <div class="spec-image">
            <img :src="spec.dishesImage" alt="" width="96px" height="96px">
          </div>
          <div class="spec-info">
            <div class="spec-name">{{ spec.dishesName }}</div>
            <div class="spec-price">{{ currencyFormatter(spec.salePrice) }} VNĐ</div>
            <div class="spec-qua">
              <div class="spec-quality">
                <StarFill :currentColor="'#EEC800'" />
                <StarFill :currentColor="'#EEC800'" />
                <StarFill :currentColor="'#EEC800'" />
                <StarFill :currentColor="'#EEC800'" />
                <StarFill :currentColor="'#EEC800'" />
              </div>
              <div class="spec-quantity">
                <!-- <div class="changeNum">
                  <span class="downNumber" @click="decreaseDish()"><i class="bi bi-dash-circle"></i></span>
                  <span>{{ spec.numberOfDish }}</span>
                  <span class="upNumber" @click="increaseDish()"><i class="bi bi-plus-circle"></i></span>
                </div> -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-if="totalOrder > 0" class="totalOrder" @click="handleOpenOrderList()">
      <!-- <nuxt-link :to="`/khach-hang/chi-tiet-order/${orderIdLocal}`"> -->
        <button>Bạn đã order ({{ totalOrder }})</button>
      <!-- </nuxt-link> -->
    </div>
    <SupportModal ref="supportModalRef" />
    </b-container>
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
import VueSlickCarousel from 'vue-slick-carousel';
import { menuManagementService, orderService, clientService, chartService } from '@/services';
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
      bestSeller: [],
      orderIdLocal: null,
      showSpModal: false,
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

    listDishSearch() {
      if (this.searchText) {
        return this.listDishes.filter((item) => {
          return this.searchText
            .toLowerCase()
            .split(' ')
            .every((v) => item.dishesName.toLowerCase().includes(v));
        });
      } else {
        return this.listDishes;
      }
    },

    totalOrder() {
      const totalDish = map(this.listDishes, (item) => item.numberOfDish);
      const total = 0;
      const totalNum = reduce(totalDish, (pre, cur) => pre + cur, total);
      return totalNum;
    }
  },

  mounted() {
    this.orderIdLocal = localStorage.getItem('orderId');
    this.getListCategory();
    this.getListDish();
    this.getBestSellerDish();
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
      this.showSpModal = true;
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

    async getBestSellerDish() {
      const res = await chartService.getBestSellerDish();

      if (res.success) {
        this.bestSeller = res.data;
      }
    },

    async getListDish() {
      const res = await clientService.getListDish();

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

      const res = await orderService.postOrderItem(requestParam).finally(() => {
        this.$router.push(`/khach-hang/chi-tiet-order/${this.orderIdLocal}`);
      });

      if (res.status === 200) {
        // this.updateOrderId(orderIdTmp);
        // this.getOrderItemList(orderIdTmp);
        // this.getTotalPayment(orderIdTmp);
        this.orderIdLocal = orderIdTmp;
      }
    },

    handleOpenOrderList() {
      this.postOrderItem();
    },

    async getOrderItemList(orderId) {
      const res = await orderService.getOrderItem(orderId);

      if (res.success) {
        // const orderList = res.data;
        const orderList = JSON.stringify(res.data);
        localStorage.setItem('listOrderItem', orderList);
        // this.updateOrderItemList(orderList);
      }
    },

    async getTotalPayment(orderId) {
      const res = await orderService.getTotalPayment(orderId);

      if (res) {
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
