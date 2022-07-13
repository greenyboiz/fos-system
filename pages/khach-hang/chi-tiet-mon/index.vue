<template>
  <DetailDish />
</template>

<router>
  {
    name: 'clientView_detailDish'
  }
</router>

<script>
import DetailDish from '@/modules/clientView/DetailDish/index.vue';
export default {
  components: { DetailDish }
};
</script>
