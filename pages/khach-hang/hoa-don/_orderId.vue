<template>
  <Receipt />
</template>

<script>
import Receipt from '@/modules/clientView/Receipt/index.vue';
export default {
  key(route) {
    return route.params.orderId;
  },

  components: { Receipt },

  layout: 'default-no-header',

  auth: false,
};
</script>
