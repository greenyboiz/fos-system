<template>
  <MenuManagement />
</template>

<script>
import MenuManagement from '@/modules/menuManagement/layout/index.vue';
export default {
  name: 'ThucDon',

  components: {
    MenuManagement,
  },

  head() {
    return {
      title: 'Thực đơn',
    };
  },
};
</script>
