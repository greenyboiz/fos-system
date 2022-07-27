<template>
  <Chef />
</template>

<script>
import Chef from '@/modules/ChefScreen/index.vue';

export default {
  name: 'Page',

  components: {
    Chef,
  },

  head() {
    return {
      title: 'Đầu bếp',
    };
  },
};
</script>
