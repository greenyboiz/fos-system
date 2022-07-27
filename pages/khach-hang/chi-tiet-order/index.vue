<template>
  <DetailOrder />
</template>

<router>
  {
    name: 'clientView_detailOrder'
  }
</router>

<script>
import DetailOrder from '@/modules/clientView/DetailOrder/index.vue';
export default {
  components: { DetailOrder },

  layout: 'default-no-header',

  auth: false,
};
</script>
