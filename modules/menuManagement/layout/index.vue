<template>
  <div class="menuManagement">
    <div class="headline">
      <div class="headline__left">
        <div class="headline__title">Quản lý thực đơn</div>
        <div class="headline__count">{{ totalDish }} món ăn</div>
      </div>
      <div class="headline__right">
        <div class="searchArea">
          <div class="searchBox d-flex align-items-center">
            <img
              class="searchBox__icon"
              src="~/assets/icons/search_gray_icon.png"
            />
            <input
              v-model="searchText"
              class="searchBox__input"
              placeholder="Tìm kiếm thông tin món ăn"
            />
            <div
              v-if="searchText"
              class="cursor-pointer"
              @click="removeKeyword()"
            >
              <img
                class="searchBox__remove"
                src="~/assets/icons/remove_search.svg"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="menuManagement__list">
      <div class="menuManagement__list--title">Danh sách thực đơn</div>
      <div class="menuManagement__list--table">
        <table class="table table-bordered table-hover">
          <thead>
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Tên món ăn</th>
              <th scope="col">Mô tả</th>
              <th scope="col">Giảm giá (%)</th>
              <th scope="col">Giá mua (đ)</th>
              <th scope="col">Giá bán (đ)</th>
              <th scope="col">Trạng thái</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in listDishSearch" :key="item.id" class="tw-cursor-pointer">
              <th scope="row">{{ item.dishesId }}</th>
              <td>{{ item.dishesName }}</td>
              <td>{{ item.description }}</td>
              <td>{{ item.discount }}</td>
              <td>{{ item.costPrice }}</td>
              <td>{{ item.salePrice }}</td>
              <td>{{ item.status === 1 ? 'Còn hàng' : 'Hết hàng' }}</td>
              <td class="align-items-center">
                <div class="btn-group align-top">
                  <button
                    class="btn btn-sm btn-outline-secondary badge"
                    type="button"
                    data-toggle="modal"
                    data-target="#myModal"
                    @click="editClick(item)"
                  >
                    Edit
                  </button>
                  <button
                    class="btn btn-sm btn-outline-secondary badge"
                    type="button"
                    @click="remove(item.dishesId)"
                  >
                    Delete
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <nav aria-label="...">
      <ul class="pagination">
        <li class="page-item disabled">
          <span class="page-link">Previous</span>
        </li>
        <li class="page-item active" aria-current="page">
          <a class="page-link" href="#">1</a>
        </li>
        <li class="page-item">
          <span class="page-link">2</span>
        </li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>
        <li class="page-item">
          <a class="page-link" href="#">Next</a>
        </li>
      </ul>
    </nav>
    <div class="add-button">
      <button class="btn btn-success" @click="handleShowAddDishesModal()">
        Thêm món ăn
      </button>
    </div>
    <AddDishModal ref="addDishModalRef" :dishesId="dishesId" @doneAdd="addedDish" @doneUpdate="updatedDish" />
  </div>
</template>

<script>
import { menuManagementService } from '@/services';
import AddDishModal from '../modals/AddDishModal/index.vue';
export default {
  name: 'MenuManagement',

  components: {
    AddDishModal,
  },

  data() {
    return {
      searchText: '',
      listDishes: [],
      dishesId: null,
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
    }
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
      const res = await menuManagementService.getListDish();

      this.listDishes = res.data;
    },

    deleteDish(dishId) {
      this.$confirmPopup.open({
        title: 'Xác nhận',
        message: 'Bạn có chắc muốn xóa món ăn này không?',
        confirmText: 'Xóa',

        confirmed: async () => {
          const res = await menuManagementService.deleteDish(dishId);

          if (res.status === 200) {
            this.getListDish();
          }
        }
      });
    }
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
>
