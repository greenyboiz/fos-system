<template>
  <div class="dinner">
    <ul class="choice">
      <template v-for="item in listTable">
        <button
          :id="item.tableId"
          :key="item.tableId"
          class="choice__btn"
          :class="{ had: !item.status, statue: item.status, active: selectedTable === item.tableId }"
          @click="onSelectTable(item.tableId, item.status)"
        >
          B{{ item.tableId }}

          <!-- <div class="choice__status">
                <img src="@/assets/images/done.png" />
              </div> -->
        </button>
      </template>
    </ul>
    <DisplayClientRequest ref="displayRqRef" />
  </div>
</template>

<script>
import { clientService } from '@/services';
import { findIndex } from 'lodash';
import SockJs from 'sockjs-client';
import StompClient from 'webstomp-client';
import DisplayClientRequest from '@/components/common/DisplayClientRequest/index.vue';

export default {
  name: 'Dinner',

  components: {
    DisplayClientRequest,
  },

  data() {
    return {
      listTable: [],
      selectedTable: null,
      clientSp: '',
      statusTable: false,
      socketTable: null,
    };
  },

  mounted() {
    this.getListTable();
    // setInterval(() => {
    //   this.getListTable();
    // }, 60000);
    this.connect();
  },

  methods: {
    connect() {
      this.socket = new SockJs('https://project-for-fos-mld.herokuapp.com/ws');
      this.stompClient = StompClient.over(this.socket);
      this.stompClient.connect({}, this.onConnected);
    },

    onConnected() {
      this.stompClient.subscribe('/topic/staffRoom', this.onMessageReceived);
    },

    onMessageReceived(payload) {
      const message = JSON.parse(payload.body);

      if (message.type === 'change_table') {
          this.clientSp = 'Đổi bàn';
        } else if (message.type === 'take_away') {
          this.clientSp = 'Mang về';
        } else if (message.type === 'call_out') {
          this.clientSp = 'Khác';
        }
      if (message.type === 'change_table' || message.type === 'take_away' || message.type === 'call_out') {
        const data = {
          tableId: message.tableId,
          rqType: this.clientSp,
        };
        this.handleDisplayClientRq(data);
      }
      this.selectedTable = message.tableId;
      // this.statusTable = false;
      const socketTableIndex = findIndex(this.listTable, (item) => item.tableId === message.tableId);
      this.listTable[socketTableIndex].status = false;

      const element = document.getElementById(`${message.tableId}`);
      element.style.backgroundColor = '#FF0000';
      element.style.color = '#fff';
    },

    handleDisplayClientRq(data) {
      this.$refs.displayRqRef.show(data);
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
      if (status === false) {
        return 'had';
      }

      if (status === true) {
        return 'statue';
      }
    },

    onSelectTable(tableId, statusTable) {
      this.selectedTable = tableId;
      const valueTable = {
        tableId: tableId,
        status: statusTable,
      };

      this.$root.$emit('valueTable', valueTable);
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
