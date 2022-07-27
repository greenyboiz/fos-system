<template>
  <Order />
</template>

<script>
import Order from '@/modules/clientView/Order/index.vue';
export default {
  key(route) {
    return route.params.orderId;
  },

  auth: false,

  components: { Order },

  layout: 'default-no-header',
};
</script>
