<template>
  <div class="tableManagement">
    <div class="headline">
      <div class="headline__left">
        <div class="headline__title">Quản lý bàn ăn</div>
        <div class="headline__count">{{ totalTable }} bàn ăn</div>
      </div>
    </div>
    <div class="table-wrapper">
      <div class="tableManagement__list">
        <div class="tableManagement__list--title">Danh sách bàn ăn</div>
        <div class="tableManagement__list--table">
          <table class="table table-bordered table-hover">
            <thead>
              <tr>
                <th scope="col">ID</th>
                <th scope="col">Số ghế</th>
                <th scope="col">Trạng thái</th>
                <th scope="col">Action</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in listTable" :key="item.tableId" class="tw-cursor-pointer" @click="handleQRCode(item)">
                <th scope="row">{{ item.tableId }}</th>
                <td>{{ item.numberOfSeats }}</td>
                <td>{{ item.status === 1 ? 'Còn trống' : 'Hết chỗ' }}</td>
                <td class="align-items-center">
                  <div class="btn-group align-top">
                    <button
                      class="btn btn-sm btn-outline-secondary badge"
                      type="button"
                      data-toggle="modal"
                      data-target="#myModal"
                      @click="editClick(item)"
                    >
                      Edit
                    </button>
                    <button
                      class="btn btn-sm btn-outline-secondary badge"
                      type="button"
                      @click="remove(item.dishesId)"
                    >
                      Delete
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="add-button">
          <button class="btn btn-success" @click="handleShowAddTablesModal()">
            Thêm bàn ăn
          </button>
        </div>
      </div>
      <div v-if="isOpenQRCode" class="tableManagement__qr">
        <div class="selected-table mb-2">
          <span>Số bàn: {{ selectedTable.id }}</span>
        </div>
        <div class="table-link mb-2">
          <span>URL Link</span>
          <input v-model="selectedTable.qr_url" type="text">
        </div>
        <div class="qr-image mb-2">
          <img src="@/assets/images/table-background.jpg" alt="">
        </div>
        <div class="add-button">
          <button class="btn btn-success" @click="handleSave()">
            Lưu mã QR
          </button>
        </div>
      </div>
    </div>
    <AddTableModal ref="addTableModalRef" :tableId="tableId" />
  </div>
</template>

<script>
import { tableManagementService } from '@/services';
import AddTableModal from '../modals/AddTableModal/index.vue';
import { filter } from 'lodash';
export default {
  name: 'TableManagement',

  components: {
    AddTableModal,
  },

  data() {
    return {
      searchText: '',
      listTable: [],
      tableId: '',
      qr_url: '',
      isOpenQRCode: false,
      selectedTable: {
        id: null,
        qr_image: '',
        qr_url: '',
        numberOfSeat: 0,
      }
    };
  },

  computed: {
    totalTable() {
      return this.listTable.length;
    },
  },

  watch: {
    selectedTable: {
      handler() {
        this.isOpenQRCode = true;
      },
      deep: true,
    }
  },

  mounted() {
    this.getListTable();
  },

  methods: {
    editClick(val) {
      this.$refs.addTableModalRef.show('update');
    },

    remove(val) {
      this.deleteTable(val);
    },

    handleShowAddTablesModal() {
      this.$refs.addTableModalRef.show();
    },

    handleQRCode(val) {
      this.selectedTable.id = val.tableId;
      this.selectedTable.qr_image = val.qrCode.qrcodeImage;
      this.selectedTable.qr_url = val.qrCode.qrcodeLink;
      this.selectedTable.numberOfSeat = val.numberOfSeats;
      this.isOpenQRCode = !this.isOpenQRCode;
    },

    handleSave() {

    },

    async getListTable() {
      const res = await tableManagementService.getListTable();

      this.listTable = res.data;
    },

    deleteTable(dishId) {
      this.$confirmPopup.open({
        title: 'Xác nhận',
        message: 'Bạn có chắc muốn xóa bàn này không?',
        confirmText: 'Xóa',

        confirmed: async () => {
          const res = await tableManagementService.deleteTable(dishId);

          if (res.status === 200) {
            this.getListTable();
          }
        }
      });
    }
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
