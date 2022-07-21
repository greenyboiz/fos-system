<template>
  <LoginPage />
</template>

<script>
import LoginPage from '@/modules/auth/pages/signin/index.vue';
export default {
  name: 'IndexPage',

  components: {
    LoginPage,
  },

  layout: 'default-no-header',

  auth: false,
};
</script>
