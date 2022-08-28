<template>
  <b-modal
    ref="swapTable"
    centered
    title="Chuyển bàn"
    body-class="modal__body"
    content-class="modal__content"
    scrollable
    hide-footer
    @hidden="handleHideModal"
  >
    <div class="dinner">
      <div class="note">
        Ghi chú: Vui lòng chọn bàn để chuyển
      </div>
      <ul class="choice">
        <template v-for="item in listTable">
          <button
            :key="item.tableId"
            class="choice__btn"
            :class="{ had: !item.status, statue: item.status, active: selectedTable === item.tableId }"
            @click="onSelectTable(item.tableId)"
          >
            B{{ item.tableId }}

              <!-- <div class="choice__status">
                    <img src="@/assets/images/done.png" />
                  </div> -->
          </button>
        </template>
      </ul>
      <div class="swapTable__label text-center">
        Chuyển từ bàn
        <span>B<input v-model.number="moveFrom" type="text"></span>
        đến bàn
        <span>B<input v-model.number="moveTo" type="text"></span>
      </div>
      <div class="swapTable__button text-center" @click="handleSwapTable()">
        <button>
          Chuyển bàn
        </button>
      </div>
    </div>
  </b-modal>
</template>

<script>
import { tableManagementService, employeeService, clientService } from '@/services';
import { filter } from 'lodash';
import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import SockJs from 'sockjs-client';
import StompClient from 'webstomp-client';
import 'vue-toast-notification/dist/theme-sugar.css';

Vue.use(VueToast, { position: 'top' });
export default {
  name: 'SwapTableModal',

  // props: {
  //   connect: {
  //     type: Function,
  //     default: Function,
  //   }
  // },

  data() {
    return {
      listTable: [],
      moveTable: [],
      moveFrom: '',
      moveTo: '',
      selectedTable: null,
    };
  },

  watch: {
  },

  mounted() {
  },

  methods: {
    show() {
      this.getListTable();
      this.connect();
      this.$refs.swapTable.show();
    },

    handleHideModal() {
      this.moveTable = [];
      this.moveFrom = '';
      this.moveTo = '';
      this.$refs.swapTable.hide();
      location.reload();
    },

    connect() {
      this.socket = new SockJs('https://project-for-fos-mld.herokuapp.com/ws');
      this.stompClient = StompClient.over(this.socket);
      this.stompClient.connect({}, this.onConnected);
    },

    onConnected() {
      this.stompClient.subscribe('/topic/staffRoom');
    },

    sendMessage(id) {
      // console.log(this.stompClient);
      console.log(id);
      if (this.stompClient) {
        const tables = {
          tableId: id,
        };
        this.stompClient.send('/app/chat.sendMessage', JSON.stringify(tables), {});
      }
    },

    async getListTable() {
      const res = await clientService.getListTableStaff({
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        this.listTable = res.data;
      }
    },

    checkStatus(status) {
      if (!status) {
        return 'had';
      }

      return 'statue';

    },

    onSelectTable(tableId) {
      this.selectedTable = tableId;
      if (this.moveTable.length === 2) {
        this.moveTable[1] = tableId;
        this.moveTo = this.moveTable[1];
      }
      if (this.moveTable.length < 2) {
        this.moveTable.push(tableId);
        this.moveFrom = this.moveTable[0];
        this.moveTo = this.moveTable[1];
      }
    },

    async handleSwapTable() {
      const fromItem = filter(this.listTable, (item) => item.tableId === this.moveFrom);
      const toItem = filter(this.listTable, (item) => item.tableId === this.moveTo);
      if (fromItem[0].status) {
        Vue.$toast.error('Bàn chuyển không được là bàn trống');
        this.moveFrom = null;
        this.moveTo = null;
        this.moveTable = [];
        return;
      }

      if (!toItem[0].status) {
        Vue.$toast.error('Bàn chuyển đến phải là bàn trống');
        this.moveTo = null;
        return;
      }

      if (!this.moveTo) {
        Vue.$toast.error('Vui lòng chọn bàn muốn chuyển đến');
        return;
      }

      const res = await employeeService.switchTable(this.moveFrom, this.moveTo, {}, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        console.log(this.moveTo);
        this.sendMessage(this.moveTo);
        localStorage.tableId = this.moveTo;
        this.moveFrom = null;
        this.moveTo = null;
        this.selectedTable = null;
        Vue.$toast.success('Chuyển bàn thành công');
        this.getListTable();
      }
    }
  }
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
