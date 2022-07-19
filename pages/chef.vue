<template>
  <Chef />
</template>

<script>
import Chef from '~/modules/chef/index.vue';

export default {
  name: 'Page',

  components: {
    Chef,
  },

  head() {
    return {
      title: 'Chef',
    };
  },
};
</script>
