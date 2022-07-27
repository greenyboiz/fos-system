<template>
  <Home />
</template>

<router>
  {
    name: 'specific_table'
  }
</router>

<script>
import Home from '@/modules/clientView/Home/index.vue';
export default {

  key(route) {
    return route.params.qrcodeId;
  },

  auth: false,

  components: { Home },

  layout: 'default-no-header'
};
</script>

<style>

</style>
