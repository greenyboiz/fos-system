<template>
  <Order />
</template>

<router>
  {
    name: 'clientView_order'
  }
</router>

<script>
import Order from '@/modules/clientView/Order/index.vue';
export default {
  components: { Order },

  layout: 'default-no-header',
};
</script>
