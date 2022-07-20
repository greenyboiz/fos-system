<template>
  <b-modal
    ref="confirmSupport"
    centered
    body-class="modal__body"
    content-class="modal__content"
    scrollable
    hide-header
    hide-footer
    hide-header-close
    @hidden="handleHideModal"
  >
    <div class="d-flex justify-content-center align-items-center confirmSupport">
      Nhân viên đã tiếp nhận yêu cầu của quý khách. Vui lòng chờ nhân viên đến xử lý!
    </div>
  </b-modal>
</template>

<script>
export default {
  name: 'ConfirmSupport',

  methods: {
    show() {
      this.$refs.confirmSupport.show();
    },

    handleHideModal() {
      this.$refs.confirmSupport.hide();
    },
  }
};
</script>
