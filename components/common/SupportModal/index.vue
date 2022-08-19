<template>
  <b-modal
    ref="supportClient"
    centered
    title="Hỗ trợ khách hàng"
    body-class="modal__body"
    content-class="modal__content"
    scrollable
    hide-footer
    hide-header-close
    @hidden="handleHideModal"
  >
    <div class="supportList">
      <div v-for="sp in listSupport" :key="sp.key" class="support_item" @click="handleConfirm(sp.key)">
        {{ sp.name }}
      </div>
    </div>
    <ConfirmSpModal ref="confirmSpModalRef" />
  </b-modal>
</template>

<script>
import ConfirmSpModal from '../ConfirmSpModal/index.vue';
import SockJs from 'sockjs-client';
import StompClient from 'webstomp-client';
export default {
  name: 'SupportModal',

  components: {
    ConfirmSpModal
  },

  data() {
    return {
      listSupport: [
        { id: 1, name: 'Hỗ trợ đổi bàn', key: 'change_table' },
        { id: 2, name: 'Mang đồ ăn về', key: 'take_away' },
        // { id: 3, name: 'Gọi thêm món', key: 'order_more' },
        { id: 4, name: 'Yêu cầu khác', key: 'call_out' }
      ],
      supportKey: '',
    };
  },

  methods: {
    show() {
      this.connect();
      this.$refs.supportClient.show();
    },

    handleHideModal() {
      this.$refs.supportClient.hide();
    },

    handleConfirm(spKey) {
      const tableId = localStorage.getItem('tableId');
      if (this.stompClient) {
        const tables = {
          tableId: tableId,
          type: spKey
        };
        if (spKey === 'change_table') {
          this.supportKey = 'ChangeTable';
        } else if (spKey === 'take_away') {
          this.supportKey = 'TakeAway';
        } else if (spKey === 'call_out') {
          this.supportKey = 'CallOut';
        }
        this.stompClient.send(`/app/chat.sendSupport${this.supportKey}`, JSON.stringify(tables), {});
      }
      this.$refs.confirmSpModalRef.show();
    },

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

      if(message.type === 'CHAT') {
          const element = document.getElementById(message.content);
          element.style.backgroundColor = '#00FF00';
      }
    },
  }
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
