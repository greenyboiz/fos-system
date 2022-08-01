<template>
  <div class="dinner">
    <ul class="choice">
      <template v-for="item in listTable">
        <button
          :key="item.tableId"
          class="choice__btn"
          :class="checkStatus(item.status)"
          @click="onSelectTable(item.tableId)"
        >
          A{{ item.tableId }}

          <!-- <div class="choice__status">
                <img src="@/assets/images/done.png" />
              </div> -->
        </button>
      </template>
    </ul>
  </div>
</template>

<script>
import { tableManagementService } from '@/services';

export default {
  name: 'Dinner',

  data() {
    return {
      listTable: [],
    };
  },

  mounted() {
    this.getListTable();
    setInterval(() => {
      this.getListTable();
    }, 60000);
  },

  methods: {
    async getListTable() {
      const res = await tableManagementService.getListTable({
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        this.listTable = res.data;
      }
    },

    checkStatus(status) {
      if (status === '0') {
        return 'had';
      }

      if (status === '1') {
        return 'statue';
      }
    },

    onSelectTable(tableId) {
      this.$root.$emit('tableId', tableId);
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
