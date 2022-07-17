<template>
  <div class="dinner">
    <ul class="choice">
      <template v-for="item in listTable">
        <button :key="item" class="choice__btn" :class="checkStatus(item.status)">
          {{ item.name }}

          <!-- <div class="choice__status">
                <img src="@/assets/images/done.png" />
              </div> -->
        </button>
      </template>
    </ul>
  </div>
</template>

<script>
import { employeeService } from '@/services';

export default {
  name: 'Dinner',

  data() {
    return {
      listTable: [
        {
          id: 1,
          name: 'A1',
          status: 1,
        },
        {
          id: 2,
          name: 'A2',
          status: 0,
        },
        {
          id: 3,
          name: 'A3',
          status: 0,
        },
        {
          id: 4,
          name: 'B1',
          status: 2,
        },
        {
          id: 5,
          name: 'B2',
          status: 0,
        },
        {
          id: 6,
          name: 'B3',
          status: 0,
        },
        {
          id: 7,
          name: 'B4',
          status: 0,
        },
        {
          id: 8,
          name: 'C1',
          status: 0,
        },
        {
          id: 9,
          name: 'C2',
          status: 0,
        },
        {
          id: 10,
          name: 'C3',
          status: 0,
        },
        {
          id: 11,
          name: 'C3',
          status: 0,
        },
        {
          id: 12,
          name: 'C3',
          status: 0,
        },
        {
          id: 13,
          name: 'C3',
          status: 0,
        },
        {
          id: 14,
          name: 'C3',
          status: 0,
        },
        {
          id: 15,
          name: 'C3',
          status: 0,
        },
        {
          id: 16,
          name: 'C3',
          status: 0,
        },
        {
          id: 17,
          name: 'C3',
          status: 0,
        },
        {
          id: 18,
          name: 'C3',
          status: 0,
        },
        {
          id: 19,
          name: 'C3',
          status: 0,
        },
        {
          id: 20,
          name: 'C3',
          status: 0,
        },
      ],
    };
  },

  mounted() {
    this.getListTable();
  },

  methods: {
    async getListTable() {
      const res = await employeeService.getTables();

      console.log(res);
    },

    checkStatus(status) {
      if (status === 1) {
        return 'had';
      }

      if (status === 2) {
        return 'statue';
      }

      if (status === 0) {
        return 'empty';
      }
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
