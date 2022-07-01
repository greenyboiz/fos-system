<template>
  <div style="display: flex; flex-direction: column; overflow: hidden; height: 100vh">
    <DefaultHeader />
    <div class="main-content">
      <slot />
    </div>
    <ConfirmPopup />
  </div>
</template>

<script>
import DefaultHeader from '../DefaultHeader/index.vue';
import ConfirmPopup from './main/ConfirmPopup/index.vue';
export default {
  components: {
    DefaultHeader,
    ConfirmPopup
  }
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
