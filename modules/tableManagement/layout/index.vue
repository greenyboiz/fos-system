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
                <th scope="col">Link QR</th>
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
                <tr v-for="(item, index) in listTable" :key="item.tableId" class="tw-cursor-pointer" @click="handleQRCode(item)">
                  <th scope="row">{{ `B${ index + 1 }` }}</th>
                  <td>{{ item.numberOfSeats }}</td>
                  <td>{{ item.qrCode.qrcodeLink }}</td>
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
                      <button class="btn__delete" @click="changeActiveTable(item.tableId)">
                        <!-- <img src="@/assets/icons/delete.png" alt="" /> -->
                        Vô hiệu hóa
                      </button>
                    </div>
                  </td>
                </tr>
              </template>
            </tbody>
          </table>
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
import { size, find } from 'lodash';
import QrCode from 'vue-qrcode-component';
import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

Vue.use(VueToast, { position: 'top' });

export default {
  name: 'TableManagement',

  components: {
    AddTableModal,
    Loading,
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

    changeActiveTable(val) {
      this.inActiveTable(val);
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

    inActiveTable(tableId) {
      const hadTable = find(this.listTable, (item) => item.tableId === tableId);
      if (!hadTable.status) {
        Vue.$toast.error('Bàn đã có người, không thể vô hiệu hóa');
        return;
      }
      this.$confirmPopup.open({
        title: 'Xác nhận',
        message: 'Bạn có chắc muốn vô hiệu hóa bàn này không?',
        confirmText: 'Vô hiệu hóa',

        confirmed: async () => {
          const res = await tableManagementService.changeActiveTable(tableId, {}, {
            headers: {
              Authorization: this.$auth.$storage._state['_token.local'],
            },
          });

          if (res.success) {
            this.getListTable();
            Vue.$toast.success('Vô hiệu hóa thành công');
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
