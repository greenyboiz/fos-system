<template>
  <div class="container">
    <div class="login-screen">
      <div class="header">MLD Restaurent</div>
      <LoginPage />
    </div>
  </div>
</template>

<script>
import LoginPage from '@/modules/auth/pages/signin/index.vue';
export default {
  name: 'IndexPage',

  components: {
    LoginPage,
  },

  layout: 'default-no-header',
};
</script>

<style lang="scss" scoped>
.container {
  background: #069255;
  height: 100vh;
  min-width: 100vw;
  display: grid;
  place-items: center;

  .login-screen {
    display: grid;
    place-items: center;
  }
}
</style>
