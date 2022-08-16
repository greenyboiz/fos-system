<template>
  <div style="padding: 12px">
    <Report />
  </div>
</template>

<script>
import Report from '@/modules/report/index.vue';
export default {
  name: 'BaoCao',

  components: {
    Report,
  },

  head() {
    return {
      title: 'Báo cáo',
    };
  },
};
</script>
