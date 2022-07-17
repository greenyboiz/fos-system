<template>
  <Employee />
</template>

<script>
import Employee from '@/modules/employee/index.vue'
export default {
  name: 'Page',

  components: {
    Employee,
  },

  head() {
    return {
      title: 'Bàn ăn',
    }
  },
}
</script>
