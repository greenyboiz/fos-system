<template>
  <Home />
</template>

<router>
  {
    name: 'clientView'
  }
</router>

<script>
import Home from '@/modules/clientView/Home/index.vue';
export default {
  components: { Home },

  layout: 'default-no-header',
};
</script>
