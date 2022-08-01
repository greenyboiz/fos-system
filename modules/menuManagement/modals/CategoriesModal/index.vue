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
import Loading from '@/components/common/Loading/index.vue';
import BEditableTable from 'bootstrap-vue-editable-table';
import {
  BIconTrash,
  BIconPencil,
  BIconX,
  BIconCheck,
  BButton,
} from 'bootstrap-vue';
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
          key: 'categoryId',
          label: 'Mã loại',
          type: 'number',
          editable: false,
          class: 'id-col',
        },
        {
          key: 'categoryName',
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
      this.isEdit = !this.isEdit;
      this.cateEdit = val.categoryName;
      console.log(val);
      this.rowUpdate = { edit: true, id: val.id };
    },

    removeCategory(val) {
      this.rowUpdate = { id: val.id, action: 'delete' };
    },

    handleAddMoreCategory() {
      this.rowUpdate = {
        edit: true,
        action: 'add',
        id: this.listCategory[this.listCategory.length - 1].categoryId + 1,
        data: {
          categoryId: this.listCategory[this.listCategory.length - 1].categoryId + 1,
          categoryName: '',
        }
      };
    },

    handleSubmit(data, update) {
      this.rowUpdate = {
        edit: false,
        id: data.id,
        action: update ? 'update' : 'cancel',
      };
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
      }
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
