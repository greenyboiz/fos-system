<template>
  <TableManagement />
</template>

<script>
import TableManagement from '@/modules/tableManagement/layout/index.vue';
export default {
  name: 'Page',

  components: {
    TableManagement,
  },

  head() {
    return {
      title: 'Bàn ăn',
    };
  },
};
</script>
