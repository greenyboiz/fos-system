<template>
  <StaffManagement />
</template>

<script>
import StaffManagement from '@/modules/staffManagement/layout/index.vue';
export default {
  name: 'QuanLyTaiKhoan',

  components: {
    StaffManagement,
  },

  head() {
    return {
      title: 'Quản lý nhân viên',
    };
  },
};
</script>
