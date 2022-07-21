<template>
  <OrderManagement />
</template>

<script>
import OrderManagement from '~/modules/orderManagement/index.vue';

export default {
  name: 'Page',

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
