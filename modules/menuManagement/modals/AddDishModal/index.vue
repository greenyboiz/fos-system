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
        <ImageOrDefault
          class="info-dish__img"
          :src="avatar || formDish.dishImage"
          alt="avatar"
        />
        <div class="info-dish__add">+</div>
      </div>
      <input
        ref="avatarRef"
        style="display: none"
        type="file"
        accept="image/*"
        @change="handleAddAvatar($event)"
      />
      <div class="info-dish__detail">
        <div class="info-dish__item">
          <label for="name">Tên món ăn:</label>
          <input
            id="name"
            v-model="formDish.dishesName"
            type="text"
            placeholder="Nhập tên món ăn"
          />
        </div>
        <div class="info-dish__item">
          <label for="username">Mô tả:</label>
          <input
            id="username"
            v-model="formDish.description"
            type="text"
            placeholder="Nhập mô tả"
          />
        </div>
        <div class="info-dish__item">
          <label for="salePrice">Giá bán:</label>
          <input
            id="salePrice"
            v-model.number="formDish.salePrice"
            type="number"
            placeholder="Nhập giá mua"
          />
        </div>
        <div class="info-dish__item">
          <label for="costPrice">Giá mua:</label>
          <input
            id="costPrice"
            v-model.number="formDish.costPrice"
            type="number"
            placeholder="Nhập giá bán"
          />
        </div>
        <div class="info-dish__item">
          <label for="discount">Giảm giá:</label>
          <input
            id="discount"
            v-model.number="formDish.discount"
            type="number"
            placeholder="Nhập phần trăm giảm"
          />
        </div>
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
        <div class="info-dish__item">
          <label for="status">Trạng thái:</label>
          <div class="d-flex" style="width: 100%">
            <div v-for="(status) in statusType" :key="status.id">
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
        </div>
      </div>
    </div>
    <div style="padding: 10px">
      <button class="dish__btn" :disabled="isLoading" @click="handleSave()">
        <!-- <Loading v-if="isLoading" color="white" /> -->
        <span>Xong</span>
      </button>
    </div>
  </b-modal>
</template>

<script>
import ImageOrDefault from '@/components/common/ImageOrDefault/index.vue';
import CustomCheckbox from '@/components/common/CustomCheckbox/index.vue';
import { menuManagementService } from '@/services';
import MultiSelect from 'vue-multiselect';
export default {
  name: 'AddUserModal',

  components: {
    ImageOrDefault,
    CustomCheckbox,
    MultiSelect,
  },

  props: {
    dishesId: {
      type: Number,
      default: 0,
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
      statusSelected: '',
      statusType: [
        { id: 1, name: 'Còn hàng' },
        { id: 2, name: 'Hết hàng' }
      ],
      listCategory: [],
      selectedCategory: {},
      isDone: false,
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
      this.modalTitle = title;
      this.getListCategory();
      this.$refs.addDish.show();
    },

    handleHideModal() {
      this.$refs.addDish.hide();
    },

    createUsers() {},

    updateUsers() {},

    chooseAvatar() {
      this.$refs.avatarRef.click();
    },

    handleAddAvatar(e) {
      const file = e.target.files;
      if (!file.length) return;

      this.formDish.dishImage = file[0];
      this.avatar = URL.createObjectURL(file[0]);
      e.target.value = '';
    },

    handleSelectCat() {
      // console.log(this.selectedCategory);
    },

    async getListCategory() {
      const res = await menuManagementService.getListCategory();

      if (res.status === 200) {
        this.listCategory = res.data;
      }
    },

    async addDish() {
      this.formDish.status = this.statusSelected;
      this.formDish.category = this.selectedCategory;
      this.formDish.dishImage = 'menu-p1.png';
      const requestParams = this.formDish;

      const res = await menuManagementService.addDish(requestParams);

      if (res.status === 200) {
        this.isDone = true;
        this.$emit('doneAdd', this.isDone);
      } else {
        this.isDone = false;
      }
    },

    async updateDish() {
      this.formDish.status = this.statusSelected;
      this.formDish.category = this.selectedCategory;
      this.formDish.dishesId = this.dishesId;

      const requestParams = this.formDish;

      const res = await menuManagementService.updateDish(requestParams);

      if (res.status === 200) {
        this.isDone = true;
        this.$emit('doneUpdate', this.isDone);
      } else {
        this.isDone = false;
      }
    },

    async getDishById() {
      const res = await menuManagementService.getDishById(this.dishesId);

      if (res.success) {
        const dish = res.data;

        this.formDish = dish;
        this.selectedCategory = dish.category;
        this.statusSelected = dish.status;
      }
    },

    handleSave() {
      if (this.modalTitle) {
        this.updateDish();
      } else {
        this.addDish();
      }
      this.handleHideModal();
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
// <style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
