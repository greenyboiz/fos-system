<template>
  <Payment />
</template>

<router>
  {
    name: 'clientView_order'
  }
</router>

<script>
import Payment from '@/modules/clientView/Payment/index.vue';
export default {
  components: { Payment },

  layout: 'default-no-header',

  auth: false,
};
</script>
