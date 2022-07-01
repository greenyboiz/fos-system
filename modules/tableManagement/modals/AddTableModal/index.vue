<template>
  <b-modal
    ref="addTable"
    centered
    :title="modalTitle ? 'Sửa bàn' : 'Thêm bàn'"
    body-class="modal__body"
    content-class="modal__content"
    scrollable
    hide-footer
    @hidden="handleHideModal"
  >

    <div style="padding: 10px">
      <button class="staff__btn" :disabled="isLoading" @click="handleSave()">
        <!-- <Loading v-if="isLoading" color="white" /> -->
        <span>Xong</span>
      </button>
    </div>
  </b-modal>
</template>

<script>
// import ImageOrDefault from '@/components/common/ImageOrDefault/index.vue';
// import CustomCheckbox from '@/components/common/CustomCheckbox/index.vue';
import { tableManagementService } from '@/services';
export default {
  name: 'AddUserModal',

  components: {
    // ImageOrDefault,
    // CustomCheckbox,
  },

  props: {
    userId: {
      type: Number,
      default: 0,
    }
  },

  data() {
    return {
      formTable: {
        fullName: '',
        profileImage: '',
        userName: '',
        password: '',
        contact: '',
        email: '',
        gender: '',
        role: {
          roleId: null,
          roleName: '',
        },
        status: null,
      },
      modalTitle: '',
      isLoading: false,
      isDone: false,
    };
  },

  computed: {
  },

  watch: {
    tableId() {
      this.getTableById();
    }
  },

  methods: {
    show(title) {
      this.modalTitle = title;
      this.$refs.addTable.show();
    },

    handleHideModal() {
      this.formTable = {
      };
      this.$refs.addTable.hide();
    },

    async addTable() {
      const requestParams = this.formTable;

      const res = await tableManagementService.addTable(requestParams);

      if (res.status === 200) {
        this.isDone = true;
        this.$emit('doneAdd', this.isDone);
      } else {
        this.isDone = false;
      }
    },

    async updateTable() {
      const requestParams = this.formUser;

      const res = await tableManagementService.updateTable(requestParams);

      if (res.status === 200) {
        this.isDone = true;
        this.$emit('doneUpdate', this.isDone);
      } else {
        this.isDone = false;
      }
    },

    async getTableById() {
      console.log(this.tableId);
      const res = await tableManagementService.getTableById(this.tableId);

      if (res.success) {
        // const user = res.data;
      }
    },

    handleSave() {
      if (this.modalTitle) {
        this.updateTable();
      } else {
        this.addTable();
      }
      this.handleHideModal();
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
