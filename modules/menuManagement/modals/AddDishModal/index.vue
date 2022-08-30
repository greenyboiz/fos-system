<template>
  <b-modal
    ref="addDish"
    centered
    :title="modalTitle ? 'Sửa món ăn' : 'Thêm món ăn'"
    body-class="modal__body"
    content-class="modal__content"
    scrollable
    hide-footer
    @hidden="handleHideModal"
  >
    <div class="info-dish">
      <div class="info-dish__avatar" @click="chooseAvatar()">
        <ImageOrDefault class="info-dish__img" :src="avatar || formDish.dishImage" alt="avatar" />
        <div class="info-dish__add">+</div>
      </div>
      <input ref="avatarRef" style="display: none" type="file" accept="image/*" @change="handleAddAvatar($event)" />
      <div class="info-dish__detail">
        <div class="info-dish__item">
          <label for="dishesName">Tên món ăn:</label>
          <input id="dishesName" v-model="formDish.dishesName" type="text" placeholder="Nhập tên món ăn" />
        </div>
        <div class="info-dish__item">
          <label for="description">Mô tả:</label>
          <input id="description" v-model="formDish.description" type="text" placeholder="Nhập mô tả" />
        </div>
        <div class="info-dish__item">
          <label for="salePrice">Giá bán:</label>
          <input id="salePrice" v-model.number="formDish.salePrice" type="number" placeholder="Nhập giá mua" min="0" />
        </div>
        <div class="info-dish__item">
          <label for="costPrice">Giá mua:</label>
          <input id="costPrice" v-model.number="formDish.costPrice" type="number" placeholder="Nhập giá bán" min="0" />
        </div>
        <!-- <div class="info-dish__item">
          <label for="discount">Giảm giá:</label>
          <input id="discount" v-model.number="formDish.discount" type="number" placeholder="Nhập phần trăm giảm" min="0" />
        </div> -->
        <div class="info-dish__item">
          <label for="category">Phân loại:</label>
          <MultiSelect
            v-model="selectedCategory"
            placeholder=""
            label="categoryName"
            :options="listCategory"
            :preselectFirst="true"
            :multiple="false"
            @input="handleSelectCat"
          />
        </div>
        <!-- <div class="info-dish__item">
          <label for="status">Trạng thái:</label>
          <div class="d-flex" style="width: 100%">
            <div v-for="status in statusType" :key="status.id">
              <CustomCheckbox
                v-model="statusSelected"
                class="mr-2"
                shape="circle"
                type="radio"
                styleCheck="dot"
                :keyValue="`${status.id}s`"
                :inputValue="status.id"
                :customLabel="true"
              >
                <template slot="custom-label"> {{ status.name }} </template>
              </CustomCheckbox>
            </div>
          </div>
        </div> -->
      </div>
    </div>
    <div style="padding: 10px">
      <button class="dish__btn" :disabled="isLoading" @click="handleSave()">
        <!-- <Loading v-if="isLoading" color="white" /> -->
        <span v-if="!modalTitle">Thêm mới</span>
        <span v-else>Cập nhật</span>
      </button>
    </div>
  </b-modal>
</template>

<script>
import ImageOrDefault from '@/components/common/ImageOrDefault/index.vue';
import CustomCheckbox from '@/components/common/CustomCheckbox/index.vue';
import { menuManagementService } from '@/services';
import MultiSelect from 'vue-multiselect';
import { size, find } from 'lodash';

import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

Vue.use(VueToast, { position: 'top' });
export default {
  name: 'AddUserModal',

  components: {
    ImageOrDefault,
    // CustomCheckbox,
    MultiSelect,
  },

  props: {
    dishesId: {
      type: Number,
      default: 0,
    },
    listDishes: {
      type: Array,
      default: () => []
    }
  },

  data() {
    return {
      formDish: {
        dishesName: '',
        description: '',
        salePrice: 0,
        costPrice: 0,
        discount: 0,
        category: {},
        dishImage: '',
        status: null,
      },
      modalTitle: '',
      avatar: '',
      isLoading: false,
      statusSelected: 1,
      statusType: [
        { id: 1, name: 'Còn hàng' },
        { id: 0, name: 'Hết hàng' },
      ],
      listCategory: [],
      selectedCategory: {},
      isDone: false,
      thisDishName: '',
    };
  },

  computed: {},

  watch: {
    dishesId() {
      this.getDishById();
    },
  },

  methods: {
    show(title) {
      if (title) {
        this.modalTitle = title;
        this.getDishById();
      } else  {
        this.modalTitle = '';
      }
      this.getListCategory();
      this.$refs.addDish.show();
    },

    handleHideModal() {
      this.formDish.dishesName = '';
      this.formDish.description = '';
      this.formDish.salePrice = 0;
      this.formDish.costPrice = 0;
      this.formDish.discount = 0;
      this.selectedCategory = {};
      this.avatar = '';
      this.formDish.dishImage = '';
      this.$refs.addDish.hide();
    },

    chooseAvatar() {
      // this.$refs.avatarRef.click();
      window.cloudinary.createUploadWidget(
        { cloud_name: 'dk7clbldt',
          upload_preset: 'ml_default'
        },
        (error, result) => {
          if (!error && result && result.event === 'success') {
            console.log('Done uploading..: ', result.info);
            this.avatar = result.info.url;
          }
        }
      ).open();
    },

    // handleAddAvatar(e) {
    //   const file = e.target.files;
    //   if (!file.length) return;

    //   this.formDish.dishImage = file[0];
    //   this.avatar = URL.createObjectURL(file[0]);
    //   e.target.value = '';
    // },

    handleSelectCat() {
    },

    async getListCategory() {
      const res = await menuManagementService.getListCategory({
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        this.listCategory = res.data;
      }
    },

    async addDish() {
      this.formDish.status = this.statusSelected;
      this.formDish.category = this.selectedCategory;
      this.formDish.dishImage = this.avatar;
      this.formDish.dishesName.trim();
      this.formDish.description.trim();
      const requestParams = this.formDish;

      const res = await menuManagementService.addDish(requestParams, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        this.isDone = true;
        this.$emit('doneAdd', this.isDone);
        Vue.$toast.success('Thêm món ăn thành công');
      } else {
        this.isDone = false;
      }
    },

    async updateDish() {
      this.formDish.status = this.statusSelected;
      this.formDish.category = this.selectedCategory;
      this.formDish.dishesId = this.dishesId;
      this.formDish.dishImage = this.avatar;

      const requestParams = this.formDish;

      const res = await menuManagementService.updateDish(requestParams, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        this.isDone = true;
        this.$emit('doneUpdate', this.isDone);
        Vue.$toast.success('Cập nhật món ăn thành công');
      } else {
        this.isDone = false;
      }
    },

    async getDishById() {
      const res = await menuManagementService.getDishById(this.dishesId, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        const dish = res.data;

        this.formDish = dish;
        this.thisDishName = dish.dishesName.trim();
        this.formDish.dishesName.trim();
        this.formDish.description.trim();
        this.selectedCategory = dish.category;
        // this.statusSelected = dish.status ? 1 : 0;
        this.avatar = dish.dishImage;
      }
    },

    handleSave() {
      if (!this.validator()) {
        return;
      }

      if (this.modalTitle) {
        this.updateDish();
      } else {
        this.addDish();
      }
      this.handleHideModal();
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

    onDishNameChange() {

    },

    validator() {
      if (!this.formDish.dishesName) {
        Vue.$toast.error('Vui lòng nhập tên Món ăn');
        document.getElementById('dishesName').focus();
        return false;
      }

      const regexDishname = /^([a-zA-Z0-9 ]){2,40}$/;

      if (!regexDishname.test(this.removeAscent(this.formDish.dishesName))) {
        Vue.$toast.error('Tên món ăn không được chứa kí tự đặc biệt và có độ dài từ 2-40 kí tự');
        this.formDish.dishesName = '';
        document.getElementById('dishesName').focus();
        return false;
      }

      // this.$watch(this.formDish.dishesName, this.onDishNameChange());

      const existDishName = find(this.listDishes, (item) => item.dishesName === this.formDish.dishesName);
      if (existDishName && !this.modalTitle) {
        Vue.$toast.error('Tên món ăn đã tồn tại');
        this.formDish.dishesName = '';
        document.getElementById('dishesName').focus();
        return false;
      }

      if (this.modalTitle) {
        if (this.formDish.dishesName !== this.thisDishName) {
          Vue.$toast.error('Tên món ăn đã tồn tại');
          this.formDish.dishesName = '';
          document.getElementById('dishesName').focus();
          return false;
        }
      }

      if (!this.formDish.description) {
        Vue.$toast.error('Vui lòng mô tả món ăn');
        document.getElementById('description').focus();
        return false;
      }

      if (!this.formDish.salePrice) {
        Vue.$toast.error('Vui lòng nhập giá bán');
        return false;
      }

      if (this.formDish.salePrice <= 0) {
        Vue.$toast.error('Giá bán phải lớn hơn 0');
        return;
      }

      if (!this.formDish.costPrice) {
        Vue.$toast.error('Vui lòng nhập giá mua');
        return false;
      }

      if (this.formDish.costPrice < 0) {
        Vue.$toast.error('Giá mua không được nhỏ hơn 0');
        return;
      }

      if (this.formDish.salePrice < this.formDish.costPrice) {
        Vue.$toast.error('Giá bán phải lớn hơn giá mua');
        return false;
      }

      if (!size(this.selectedCategory)) {
        Vue.$toast.error('Vui lòng phân loại sản phẩm');
        return false;
      }


      if (this.statusSelected === '') {
        Vue.$toast.error('Vui lòng chọn trạng thái');
        return false;
      }

      if (!this.avatar) {
        Vue.$toast.error('Vui lòng thêm ảnh cho món');
        return;
      }

      return true;
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
//
<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
