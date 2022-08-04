<template>
  <Employee />
</template>

<script>
import Employee from '@/modules/employee/index.vue';
export default {
  name: 'NhanVien',

  components: {
    Employee,
  },

  head() {
    return {
      title: 'Nhân viên',
    };
  },
};
</script>
