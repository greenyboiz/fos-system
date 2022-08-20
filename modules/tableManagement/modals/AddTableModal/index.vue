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
          <!-- <input id="numOfSeat" v-model.number="formTable.numberOfSeats" type="number" placeholder="Nhập số ghế" min="0" /> -->
          <MultiSelect
            v-model.number="formTable.numberOfSeats"
            placeholder="Chọn số ghế"
            :options="listSeat"
            :multiple="false"
          />
        </div>

        <div class="info-table__item">
          <label for="qrlink">QR Code Link:</label>
          <input id="qrlink" v-model="formTable.qrCode.qrcodeLink" type="text" placeholder="Nhập link QR" />
        </div>
      </div>

      <div class="info-table__qr">
        <qr-code class="info-table__code" :size="250" :text="formTable.qrCode.qrcodeLink"></qr-code>
      </div>
    </div>
    <div style="padding: 10px">
      <button class="staff__btn" :disabled="isLoading" @click="handleSave()">
        <!-- <Loading v-if="isLoading" color="white" /> -->
        <span v-if="!modalTitle">Thêm mới</span>
        <span v-else>Cập nhật</span>
      </button>
    </div>
  </b-modal>
</template>

<script>
import { tableManagementService } from '@/services';
import QrCode from 'vue-qrcode-component';
import MultiSelect from 'vue-multiselect';
import { toNumber, find } from 'lodash';

import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

Vue.use(VueToast, { position: 'top' });

export default {
  name: 'AddUserModal',

  components: {
    QrCode,
    MultiSelect
  },

  props: {
    tableId: {
      type: Number,
      default: 1,
    },
    listTable: {
      type: Array,
      default: () => []
    }
  },

  data() {
    return {
      formTable: {
        numberOfSeats: 0,
        qrCode: {
          qrcodeLink: '',
        },
        status: null,
      },
      modalTitle: '',
      isLoading: false,
      isDone: false,
      statusSelected: 2,
      statusType: [
        { id: 0, name: 'Đã đầy' },
        { id: 1, name: 'Còn trống' },
      ],
      listSeat: [2, 4, 6, 8, 10, 12]
    };
  },

  watch: {
    tableId() {
      this.getDetailTable();
    }
  },

  methods: {
    show(title) {
      if (title) {
        this.modalTitle = title;
        this.getDetailTable();
      } else {
        this.modalTitle = '';
      }
      this.$refs.addTable.show();
    },

    handleHideModal() {
      this.formTable = {
        numberOfSeats: 0,
        qrCode: {
          qrcodeLink: '',
        },
        status: null,
      };
      this.$refs.addTable.hide();
    },

    async getDetailTable() {
      const res = await tableManagementService.getTableById(this.tableId, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        const data = res.data;
        this.formTable = {
          numberOfSeats: data.numberOfSeats,
          qrCode: data.qrCode,
          status: toNumber(data.status),
        };
        this.statusSelected = toNumber(data.status);
      }
    },

    validator() {
      if (!this.formTable.numberOfSeats) {
        Vue.$toast.error('Vui lòng chọn số ghế');
        // document.getElementById('numOfSeat').focus();
        return false;
      }

      if (this.formTable.numberOfSeats <= 0) {
        Vue.$toast.error('Số ghế phải lớn hơn 0');
        this.formTable.numberOfSeats = '';
        // document.getElementById('numOfSeat').focus();
        return false;
      }

      const regexNumSeat = /^([0-9]{1,})$/;

      if (!regexNumSeat.test(this.formTable.numberOfSeats)) {
        Vue.$toast.error('Số ghế phải là số tự nhiên');
        this.formTable.numberOfSeats = '';
        // document.getElementById('numOfSeat').focus();
        return false;
      }

      if (!this.formTable.qrCode.qrcodeLink) {
        Vue.$toast.error('Vui lòng nhập link của mã QR');
        document.getElementById('qrlink').focus();
        return false;
      }

      const existQrcode = find(this.listTable, (item) => item.qrCode.qrcodeLink === this.formTable.qrCode.qrcodeLink);
      if (existQrcode && !this.modalTitle) {
        Vue.$toast.error('Mã QR đã tồn tại');
        document.getElementById('qrlink').focus();
        return false;
      }
      return true;
    },

    async addTable(requestParams) {
      const res = await tableManagementService.addTable(requestParams, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        this.$emit('complete');
        Vue.$toast.success('Thêm bàn thành công');
      } else {
        this.isDone = false;
      }
    },

    async updateTable(requestParams) {
      const res = await tableManagementService.updateTable(requestParams, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        this.$emit('complete');
        Vue.$toast.success('Cập nhật bàn thành công');
      } else {
        this.isDone = false;
      }
    },

    handleSave() {
      if (!this.validator()) {
        return;
      }
      const requestParams = {
        numberOfSeats: this.formTable.numberOfSeats,
        qrCode: {
          qrcodeLink: this.formTable.qrCode.qrcodeLink,
        },
        status: 1,
      };

      if (this.modalTitle === 'update') {
        requestParams.tableId = this.tableId;
        this.updateTable(requestParams);
      } else {
        this.addTable(requestParams);
      }
      this.handleHideModal();
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
