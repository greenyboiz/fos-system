<template>
  <b-modal
    ref="categoryRef"
    centered
    title="Các loại món ăn"
    body-class="modal__body"
    content-class="modal__content"
    scrollable
    hide-footer
    @hidden="handleHideModal"
  >
    <div class="categoryWrapper">
      <div class="addCategory mb-2" @click="handleAddMoreCategory()">
        <button>Thêm loại mới</button>
      </div>
      <b-editable-table
        v-model="listCategory"
        disableDefaultEdit
        :rowUpdate="rowUpdate"
        :editMode="'row'"
        bordered
        class="editable-table"
        :fields="fields"
      >
        <template #cell(edit)="data">
          <div v-if="data.isEdit">
            <BIconX
              class="edit-icon"
              @click="handleSubmit(data, false)"
            ></BIconX>
            <BIconCheck
              class="edit-icon"
              @click="handleSubmit(data, true)"
            ></BIconCheck>
          </div>
          <BIconPencil
            v-else
            class="edit-icon"
            @click="editClick(data, true)"
          ></BIconPencil>
        </template>
        <template #cell(delete)="data">
          <BIconTrash
            class="remove-icon"
            @click="removeCategory(data)"
          ></BIconTrash>
        </template>
      </b-editable-table>
    </div>
  </b-modal>
</template>

<script>
import { menuManagementService } from '@/services';
import { map } from 'lodash';
import Loading from '@/components/common/Loading/index.vue';
import BEditableTable from 'bootstrap-vue-editable-table';
import {
  BIconTrash,
  BIconPencil,
  BIconX,
  BIconCheck,
  BButton,
} from 'bootstrap-vue';
import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

Vue.use(VueToast, { position: 'top' });
export default {
  name: 'CategoriesModal',

  components: {
    BIconTrash,
    BIconPencil,
    BIconX,
    BIconCheck,
    BEditableTable,
  },

  data() {
    return {
      listCategory: [],
      isEdit: false,
      cateEdit: '',
      isLoad: false,
      rowUpdate: {},
      fields: [
        { key: 'delete', label: '' },
        {
          key: 'id',
          label: 'Mã loại',
          type: 'number',
          editable: false,
          class: 'id-col',
        },
        {
          key: 'name',
          label: 'Tên loại',
          type: 'text',
          editable: true,
          class: 'name-col',
          placeholder: 'Nhập tên loại...',
        },
        { key: 'edit', label: '' },
      ],
    };
  },

  methods: {
    show() {
      this.getListCategory();
      this.$refs.categoryRef.show();
    },

    handleHideModal() {
      this.$refs.categoryRef.hide();
    },

    editClick(val) {
      this.isEdit = true;
      this.cateEdit = val.item.name;
      console.log(val);
      this.rowUpdate = { edit: true, id: val.id };
    },

    removeCategory(val) {
      console.log('sasa');
      const catId = val.item.categoryId;
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
            this.rowUpdate = { id: val.id, action: 'delete' };
          }
        },
      });
    },

    handleAddMoreCategory() {
      const newId = this.listCategory[this.listCategory.length - 1].categoryId + 1;
      // this.rowUpdate = {
      //   edit: true,
      //   action: 'add',
      //   id: newId,
      //   data: {
      //     categoryId: newId,
      //     categoryName: '',
      //     id: newId,
      //     name: '',
      //   }
      // };
      this.listCategory.push({
        categoryId: newId,
        categoryName: '',
      });
      this.rowUpdate = { edit: true, id: newId };
    },

    handleSubmit(data, update) {
      this.rowUpdate = {
        edit: false,
        id: data.id,
        action: update ? 'update' : 'cancel',
      };
      if (this.rowUpdate.action === 'cancel') {
        return;
      }
      console.log(data.item.name);
      // console.log(this.cateEdit);
      const reqParam = {
        categoryId: data.item.categoryId,
        categoryName: data.item.name,
      };

      if (this.isEdit) {
        this.updateCategory(reqParam);
      } else {
        this.addNewCategory({
          categoryName: data.item.name,
        });
      }
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
      }
    }
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
