<template>
  <div class="dinner">
    <ul class="choice">
      <template v-for="item in listTable">
        <button
          :key="item.tableId"
          class="choice__btn"
          :class="{ had: !item.status, statue: item.status, active: selectedTable === item.tableId }"
          @click="onSelectTable(item.tableId, item.status)"
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
      selectedTable: null,
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
      if (status === false) {
        return 'had';
      }

      if (status === true) {
        return 'statue';
      }
    },

    onSelectTable(tableId, statusTable) {
      this.selectedTable = tableId;
      if (statusTable) {
        this.$root.$emit('valueTable', statusTable);
        return;
      }
      this.$root.$emit('valueTable', tableId);
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
