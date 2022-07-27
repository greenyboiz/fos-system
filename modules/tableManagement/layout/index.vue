<template>
  <div class="tableManagement">
    <div class="headline">
      <div class="headline__left">
        <div class="headline__title">Quản lý bàn ăn</div>
        <div class="headline__count">{{ size(listTable) }} bàn ăn</div>
      </div>
    </div>

    <div class="table-wrapper">
      <!--  -->
      <div class="tableManagement__list">
        <div class="tableManagement__list--title">
          <h3 class="title">Danh sách bàn ăn</h3>
          <div class="add-button">
            <button class="btn__add" @click="handleShowAddTablesModal()">Thêm bàn ăn</button>
          </div>
        </div>

        <div class="tableManagement__list--table">
          <table class="table table-bordered table-hover">
            <thead>
              <tr>
                <th scope="col">Bàn số</th>
                <th scope="col">Số ghế</th>
                <th scope="col">Trạng thái</th>
                <th scope="col">Thao tác</th>
              </tr>
            </thead>
            <tbody>
              <template v-if="isLoading">
                <div class="loading">
                  <Loading />
                </div>
              </template>

              <template v-else>
                <tr v-for="item in listTable" :key="item.tableId" class="tw-cursor-pointer" @click="handleQRCode(item)">
                  <th scope="row">{{ item.tableId }}</th>
                  <td>{{ item.numberOfSeats }}</td>
                  <td>{{ item.status === 1 ? 'Còn trống' : 'Hết chỗ' }}</td>
                  <td class="align-items-center">
                    <div class="btn-group align-top">
                      <button
                        class="btn__edit"
                        data-toggle="modal"
                        data-target="#myModal"
                        @click="editClick(item.tableId)"
                      >
                        Edit
                      </button>
                      <button class="btn__delete" @click="remove(item.tableId)">
                        <img src="@/assets/icons/delete.png" alt="" />
                      </button>
                    </div>
                  </td>
                </tr>
              </template>
            </tbody>
          </table>
        </div>
      </div>

      <!--  -->
      <div v-if="isOpenQRCode" class="tableManagement__qr">
        <div class="qr">
          <div class="qr__title title">Bàn số {{ selectedTable.id }}</div>
          <div class="qr__link">
            URL link: <input v-model="selectedTable.qr_url" placeholder="Vui lòng nhập link qr" type="text" />
          </div>

          <div class="add-button">
            <button class="btn__add" @click="handleSave()">
              <a href=""></a>
            </button>
          </div>
        </div>

        <div class="qr__img">
          <QrCode id="specificQrCode" class="qr__code" :size="400" :text="selectedTable.qr_url"></QrCode>
        </div>
      </div>
    </div>

    <AddTableModal ref="addTableModalRef" :tableId="tableId" @complete="complete" />
  </div>
</template>

<script>
import Loading from '@/components/common/Loading/index.vue';

import { tableManagementService } from '@/services';
import AddTableModal from '../modals/AddTableModal/index.vue';
import { size, forEach } from 'lodash';
import QrCode from 'vue-qrcode-component';

export default {
  name: 'TableManagement',

  components: {
    AddTableModal,
    Loading,
    QrCode,
  },

  data() {
    return {
      searchText: '',
      listTable: [],
      tableId: 0,
      qr_url: '',
      isOpenQRCode: false,
      selectedTable: {
        id: null,
        qr_image: '',
        qr_url: '',
        numberOfSeat: 0,
      },
      isLoading: false,
    };
  },

  watch: {
    selectedTable: {
      handler() {
        this.isOpenQRCode = true;
      },
      deep: true,
    },
  },

  beforeMount() {
    const qrImg = document.getElementById('specificQrCode');
  },

  mounted() {
    this.getListTable();
  },

  methods: {
    size,
    editClick(id) {
      this.tableId = id;
      this.isOpenQRCode = false;
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

    async getListTable() {
      this.isLoading = true;
      const res = await tableManagementService
        .getListTable({
          headers: {
            Authorization: this.$auth.$storage._state['_token.local'],
          },
        })
        .finally(() => {
          this.isLoading = false;
        });

      this.listTable = res.data;
    },

    deleteTable(dishId) {
      this.$confirmPopup.open({
        title: 'Xác nhận',
        message: 'Bạn có chắc muốn xóa bàn này không?',
        confirmText: 'Xóa',

        confirmed: async () => {
          const res = await tableManagementService.deleteTable(dishId, {
            headers: {
              Authorization: this.$auth.$storage._state['_token.local'],
            },
          });

          if (res.status === 200) {
            this.getListTable();
          }
        },
      });
    },

    complete() {
      this.getListTable();
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
