<template>
  <TableManagement />
</template>

<script>
import TableManagement from '@/modules/tableManagement/layout/index.vue';
export default {
  name: 'BanAn',

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
