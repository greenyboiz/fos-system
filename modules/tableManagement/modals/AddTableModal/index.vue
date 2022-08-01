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
          <input id="numOfSeat" v-model.number="formTable.numberOfSeats" type="number" placeholder="Nhập số ghế" />
        </div>

        <div class="info-table__item">
          <label for="link">QR Code Link:</label>
          <input id="link" v-model="formTable.qrCode.qrcodeLink" type="text" placeholder="Nhập link QR" />
        </div>

        <div class="info-table__item a">
          <div class="b">Trạng thái:</div>
          <div class="d-flex tw-justify-around" style="width: 100%">
            <template v-for="status in statusType">
              <CustomCheckbox
                :key="status.id"
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
            </template>
          </div>
        </div>
      </div>

      <div class="info-table__qr">
        <qr-code class="info-table__code" :size="250" :text="formTable.qrCode.qrcodeLink"></qr-code>
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
import QrCode from 'vue-qrcode-component';
import { toNumber } from 'lodash';

import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

Vue.use(VueToast, { position: 'top' });

export default {
  name: 'AddUserModal',

  components: {
    // ImageOrDefault,
    CustomCheckbox,
    QrCode,
  },

  props: {
    tableId: {
      type: Number,
      default: 1,
    },
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
      statusSelected: null,
      statusType: [
        { id: 0, name: 'Đã đầy' },
        { id: 1, name: 'Còn trống' },
      ],
    };
  },

  watch: {
    tableId() {
      this.getDetailTable();
    }
  },

  methods: {
    show(title) {
      this.modalTitle = title;

      if (this.modalTitle === 'update') {
        this.getDetailTable();
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
      if (!this.formTable.qrCode.qrcodeLink) {
        Vue.$toast.error('Vui lòng nhập link của mã QR');
        return;
      }

      if (!this.statusSelected) {
        Vue.$toast.error('Vui lòng chọn trạng thái bàn');
      }
    },

    async addTable(requestParams) {
      const res = await tableManagementService.addTable(requestParams, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        this.$emit('complete');
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
        status: this.statusSelected,
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
