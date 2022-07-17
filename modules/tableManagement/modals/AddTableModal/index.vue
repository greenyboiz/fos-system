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
    <div class="info-table">
      <div class="info-table__detail">
        <div class="info-table__item">
          <label for="numOfSeat">Số lượng ghế:</label>
          <input
            id="numOfSeat"
            v-model.number="formTable.numberOfSeats"
            type="text"
            placeholder="Nhập số ghế"
          />
        </div>
        <div class="info-table__item">
          <label for="link">QR Code Link:</label>
          <input
            id="link"
            v-model="formTable.qrCode.qrcodeLink"
            type="text"
            placeholder="Nhập link QR"
          />
        </div>
        <div class="info-table__item">
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
      <button class="staff__btn" :disabled="isLoading" @click="handleSave()">
        <!-- <Loading v-if="isLoading" color="white" /> -->
        <span>Xong</span>
      </button>
    </div>
  </b-modal>
</template>

<script>
// import ImageOrDefault from '@/components/common/ImageOrDefault/index.vue';
import CustomCheckbox from '@/components/common/CustomCheckbox/index.vue';
import { tableManagementService } from '@/services';
export default {
  name: 'AddUserModal',

  components: {
    // ImageOrDefault,
    CustomCheckbox,
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
        numberOfSeats: 0,
        qrCode: {
          qrcodeLink: ''
        },
        status: null,
      },
      modalTitle: '',
      isLoading: false,
      isDone: false,
      statusSelected: null,
      statusType: [
        { id: 0, name: 'Còn trống' },
        { id: 1, name: 'Đã đầy' }
      ],
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
      this.formTable = {};
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
