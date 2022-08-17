<template>
  <b-modal
    ref="categoryRef"
    centered
    title="Danh mục món ăn"
    body-class="modal__body"
    content-class="modal__content"
    scrollable
    hide-footer
    @hidden="handleHideModal"
  >
    <div class="categoryWrapper">
      <div class="actionCate">
        <input v-model="formData.categoryName" type="text" placeholder="Tên danh mục">
        <div class="addCategory" @click="handleSubmit()">
          <button v-if="!isEdit">Thêm mới</button>
          <button v-else>Cập nhật</button>
        </div>
        <!-- <div v-else class="addCategory" @click="handleSubmit()">
        </div> -->
      </div>
      <div class="category">
        <div class="category__head">
          <div class="category__col">Mã loại</div>
          <div class="category__col">Tên loại</div>
          <div class="category__col">Thao tác</div>
        </div>

        <div class="category__list">
          <template v-if="isLoad">
            <div class="loading">
              <Loading />
            </div>
          </template>

          <template v-else>
            <div
              v-for="(item, i) in listCategory"
              :key="'cate' + i"
              class="category__item"
            >
              <div class="category__row">L{{ item.categoryId }}</div>
              <div class="category__row">{{ item.categoryName }}</div>
              <div class="category__row align-items-center">
                <div class="btn-group align-top">
                  <button
                    class="btn__edit"
                    data-toggle="modal"
                    data-target="#myModal"
                    @click="editClick(item)"
                  >
                    Sửa
                  </button>
                  <button class="btn__delete" @click="removeCategory(item.categoryId)">
                    <img src="@/assets/icons/delete.png" alt="" />
                  </button>
                </div>
              </div>
            </div>
          </template>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import { menuManagementService } from '@/services';
import { map } from 'lodash';
import Loading from '@/components/common/Loading/index.vue';

import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

Vue.use(VueToast, { position: 'top' });
export default {
  name: 'CategoriesModal',

  components: {
    Loading
  },

  data() {
    return {
      listCategory: [],
      isEdit: false,
      isLoad: false,
      formData: {
        categoryId: null,
        categoryName: ''
      }
    };
  },

  methods: {
    show() {
      this.getListCategory();
      this.$refs.categoryRef.show();
    },

    handleHideModal() {
      this.formData.categoryName = '';
      this.isEdit = false;
      this.$refs.categoryRef.hide();
    },

    editClick(val) {
      this.isEdit = true;
      this.formData.categoryId = val.categoryId;
      this.formData.categoryName = val.categoryName;
    },

    removeAscent(str) {
      if (str === null || str === undefined) return str;

      str = str.toLowerCase();
      str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, 'a');
      str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, 'e');
      str = str.replace(/ì|í|ị|ỉ|ĩ/g, 'i');
      str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, 'o');
      str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, 'u');
      str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, 'y');
      str = str.replace(/đ/g, 'd');
      return str;
    },

    validator() {
      if (!this.formData.categoryName) {
        Vue.$toast.error('Vui lòng nhập tên danh mục');
        return false;
      }

      const regexDishname = /^([a-zA-Z0-9 ]){2,40}$/;

      if (!regexDishname.test(this.removeAscent(this.formData.categoryName))) {
        Vue.$toast.error('Tên danh mục không được chứa kí tự đặc biệt và có độ dài từ 2-40 kí tự');
        return false;
      }
      return true;
    },

    handleSubmit() {
      console.log(this.isEdit);
      if (!this.validator()) {
        return;
      }
      const reqParam = {
        categoryName: this.formData.categoryName,
      };

      if (this.isEdit) {
        reqParam.categoryId = this.formData.categoryId;
        this.updateCategory(reqParam);
      } else {
        this.addNewCategory(reqParam);
      }
    },

    removeCategory(val) {
      const catId = val;
      this.$confirmPopup.open({
        title: 'Xác nhận',
        message: 'Bạn có chắc muốn xóa loại này không?',
        confirmText: 'Xóa',

        confirmed: async () => {
          const res = await menuManagementService.deleteCategory(catId, {
            headers: {
              Authorization: this.$auth.$storage._state['_token.local'],
            },
          });

          if (res.success) {
            this.getListCategory();
          }
        },
      });
    },

    async getListCategory() {
      this.isLoad = true;
      const res = await menuManagementService
        .getListCategory({
          headers: {
            Authorization: this.$auth.$storage._state['_token.local'],
          },
        })
        .finally(() => {
          this.isLoad = false;
        });

      if (res.success) {
        this.listCategory = res.data;
        map(this.listCategory, (item) => {
          item.id = item.categoryId;
          item.name = item.categoryName;
        });
      }
    },

    async addNewCategory(reqParam) {
      const res = await menuManagementService.addCategory(reqParam, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        Vue.$toast.success('Thêm mới thành công');
        this.getListCategory();
      }
    },

    async updateCategory(reqParam) {
      const res = await menuManagementService.updateCategory(reqParam, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        Vue.$toast.success('Chỉnh sửa thành công');
        this.getListCategory();
        this.isEdit = false;
        this.formData.categoryId = null;
        this.formData.categoryName = '';
      }
    }
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
