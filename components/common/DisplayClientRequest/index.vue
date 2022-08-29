<template>
  <b-modal
    ref="supportClient"
    centered
    title="Yêu cầu hỗ trợ"
    body-class="modal__body"
    content-class="modal__content"
    scrollable
    hide-footer
    @hidden="handleHideModal"
  >
    <div class="supportType">
      <span>Bàn B{{ tableRq }} cần hỗ trợ {{ rqType }}</span>
    </div>
  </b-modal>
</template>

<script>
export default {
  name: 'DisplayClientRequest',

  data() {
    return {
      tableRq: null,
      rqType: '',
    };
  },

  methods: {
    show(data) {
      if (data) {
        this.tableRq = data.tableId;
        this.rqType = data.rqType;
      }
      this.$refs.supportClient.show();
    },

    handleHideModal() {
      this.$refs.supportClient.hide();
    },
  }
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
