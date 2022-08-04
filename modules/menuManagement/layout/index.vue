<template>
  <div class="menuManagement">
    <!-- Top -->
    <div class="headline">
      <div class="headline__top">
        <div class="headline__title">Quản lý thực đơn</div>

        <div class="headline__right">
          <button class="btn__add mr-2" @click="handleShowAddDishesModal()">Thêm món ăn</button>
          <button class="btn__add" @click="handleShowCategoryModal()">Các loại món ăn</button>

          <div class="searchArea">
            <div class="searchBox d-flex align-items-center">
              <img class="searchBox__icon" src="~/assets/icons/search_gray_icon.png" />
              <input v-model="searchText" class="searchBox__input" placeholder="Tìm kiếm thông tin món ăn" />
              <div v-if="searchText" class="cursor-pointer" @click="removeKeyword()">
                <img class="searchBox__remove" src="~/assets/icons/remove_search.svg" />
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="headline__bottom">
        <span>Danh sách thực đơn</span> -
        <span class="headline__count">{{ totalDish }} món ăn</span>
      </div>
    </div>

    <!-- Content -->

    <div class="main">
      <div class="table">
        <div class="table__wrapper">
          <div class="table__head">
            <div class="tableCol">STT</div>
            <div class="tableCol">Tên món ăn</div>
            <div class="tableCol">Mô tả</div>
            <div class="tableCol">Phân loại</div>
            <div class="tableCol">Giá mua (đ)</div>
            <div class="tableCol">Giá bán (đ)</div>
            <div class="tableCol">Trạng thái</div>
            <div class="tableCol">Action</div>
          </div>

          <div class="table__bot">
            <template v-if="isLoading">
              <div class="loading">
                <Loading />
              </div>
            </template>

            <template v-else>
              <div v-for="item in listDishSearch" :key="item.id" class="table__body">
                <div class="tableRow">{{ `D${item.dishesId}` }}</div>
                <div class="tableRow">{{ item.dishesName }}</div>
                <div class="tableRow">{{ item.description }}</div>
                <div class="tableRow">{{ item.category.categoryName }}</div>
                <div class="tableRow">{{ currencyFormatter(item.costPrice) }}</div>
                <div class="tableRow">{{ currencyFormatter(item.salePrice) }}</div>
                <div class="tableRow">
                  <span v-if="item.status" class="status had">Còn hàng</span>
                  <span v-else class="status sold">Hết hàng</span>
                </div>
                <div class="tableRow align-items-center">
                  <div class="btn-group align-top">
                    <button class="btn__edit" data-toggle="modal" data-target="#myModal" @click="editClick(item)">
                      Edit
                    </button>
                    <button class="btn__delete" @click="remove(item.dishesId)">
                      <img src="@/assets/icons/delete.png" alt="" />
                    </button>
                  </div>
                </div>
              </div>
            </template>
          </div>
        </div>
      </div>

      <!-- Paging -->
      <div class="pagination">
        <section class="pagination__wrap">
          <div class="pagination__list">
            <button class="pagination__button active">1</button>
            <button class="pagination__button">2</button>
          </div>
        </section>
      </div>
    </div>

    <AddDishModal ref="addDishModalRef" :dishesId="dishesId" @doneAdd="addedDish" @doneUpdate="updatedDish" />
    <CategoriesModal ref="categoriesModalRef" />
  </div>
</template>

<script>
import { menuManagementService } from '@/services';
import AddDishModal from '../modals/AddDishModal/index.vue';
import CategoriesModal from '../modals/CategoriesModal/index.vue';
import Loading from '@/components/common/Loading/index.vue';
import commonMixin from '@/plugins/commonMixin';

export default {
  name: 'MenuManagement',

  components: {
    AddDishModal,
    Loading,
    CategoriesModal
  },

  mixins: [commonMixin],

  data() {
    return {
      searchText: '',
      listDishes: [],
      dishesId: null,
      isLoading: false,
    };
  },

  computed: {
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

    totalDish() {
      return this.listDishes.length;
    },
  },

  mounted() {
    this.getListDish();
  },

  methods: {
    removeKeyword() {
      this.searchText = '';
    },

    editClick(val) {
      this.dishesId = val.dishesId;
      this.$refs.addDishModalRef.show('update');
    },

    remove(val) {
      this.deleteDish(val);
    },

    handleShowAddDishesModal() {
      this.$refs.addDishModalRef.show();
    },

    handleShowCategoryModal() {
      this.$refs.categoriesModalRef.show();
    },

    addedDish(val) {
      if (val) {
        this.getListDish();
      }
    },

    updatedDish(val) {
      if (val) {
        this.getListDish();
      }
    },

    async getListDish() {
      this.isLoading = true;

      const res = await menuManagementService
        .getListDish({
          headers: {
            Authorization: this.$auth.$storage._state['_token.local'],
          },
        })
        .finally(() => {
          this.isLoading = false;
        });

      if (res.content) {
        this.listDishes = res.content;
      }

    },

    deleteDish(dishId) {
      this.$confirmPopup.open({
        title: 'Xác nhận',
        message: 'Bạn có chắc muốn xóa món ăn này không?',
        confirmText: 'Xóa',

        confirmed: async () => {
          const res = await menuManagementService.deleteDish(dishId, {
            headers: {
              Authorization: this.$auth.$storage._state['_token.local'],
            },
          });

          if (res.success) {
            this.getListDish();
          }
        },
      });
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
