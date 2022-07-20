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
      <div v-for="sp in listSupport" :key="sp.key" class="support_item" @click="handleConfirm()">
        {{ sp.name }}
      </div>
    </div>
    <ConfirmSpModal ref="confirmSpModalRef" />
  </b-modal>
</template>

<script>
import ConfirmSpModal from '../ConfirmSpModal/index.vue';
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
        { id: 3, name: 'Gọi thêm món', key: 'order_more' },
        { id: 4, name: 'Yêu cầu khác', key: 'call_out' }
      ]
    };
  },

  methods: {
    show() {
      this.$refs.supportClient.show();
    },

    handleHideModal() {
      this.$refs.supportClient.hide();
    },

    handleConfirm() {
      this.$refs.confirmSpModalRef.show();
    }
  }
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
