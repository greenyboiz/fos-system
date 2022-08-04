<template>
  <OrderManagement />
</template>

<script>
import OrderManagement from '~/modules/orderManagement/index.vue';

export default {
  name: 'DonHang',

  components: {
    OrderManagement,
  },

  middleware: 'auth',

  head() {
    return {
      title: 'Đơn hàng',
    };
  },
};
</script>
