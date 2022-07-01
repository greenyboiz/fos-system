<template>
  <div class="notification is-danger w-100">
    {{ message }}
  </div>
</template>

<script>
export default {
  name: 'Notification',

  props: {
    message: {
      type: String,
      default: () => ''
    }
  },
};
</script>

<style>
.is-danger {
  text-align: center;
  background: #fb3a3a;
  padding: 10px 15px;
  color: #fff;
  display: inline-block;
  border-radius: 7px;
  margin-bottom: 10px;
}
</style>
