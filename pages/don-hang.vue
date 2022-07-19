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

  head() {
    return {
      title: 'Đơn hàng',
    };
  },
};
</script>
