<template>
  <DetailOrder />
</template>

<script>
import DetailOrder from '@/modules/clientView/DetailOrder/index.vue';
export default {
  key(route) {
    return route.params.orderId;
  },

  components: { DetailOrder },

  layout: 'default-no-header',

  auth: false,
};
</script>
