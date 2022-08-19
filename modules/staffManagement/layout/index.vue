<template>
  <div class="staffManagement">
    <!-- Top -->
    <div class="headline">
      <div class="headline__top">
        <div class="headline__title">Quản lý nhân viên</div>

        <div class="headline__right">
          <button class="btn__add" @click="handleShowAddUserModal()">Thêm nhân viên</button>

          <div class="searchArea">
            <div class="searchBox d-flex align-items-center">
              <img class="searchBox__icon" src="~/assets/icons/search_gray_icon.png" />
              <input v-model="searchText" class="searchBox__input" placeholder="Tìm kiếm thông tin nhân viên" />
              <div v-if="searchText" class="cursor-pointer" @click="removeKeyword()">
                <img class="searchBox__remove" src="~/assets/icons/remove_search.svg" />
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="headline__bottom">
        <span>Danh sách nhân viên</span> -
        <span class="headline__count">{{ totalStaff }} nhân viên</span>
      </div>
    </div>

    <!-- Content -->

    <div class="main">
      <div class="table__wrapper">
        <!-- <table class="table__wrapper">
          <div class="table__head">
            <div class="table__col">ID</div>
            <div class="table__col">Họ và tên</div>
            <div class="table__col">Tên tài khoản</div>
            <div class="table__col">Giới tính</div>
            <div class="table__col">SĐT</div>
            <div class="table__col">Email</div>
            <div class="table__col">Role</div>
            <div class="table__col">Trạng thái</div>
            <div class="table__col">Action</div>
          </div>

          <tbody class="table__bot">
            <template v-if="isLoading">
              <div class="loading">
                <Loading />
              </div>
            </template>

            <template v-else>
              <div v-for="(item, index) in pageOfItems" :key="'staff' + item.userId" class="table__body">
                <div class="table__row">{{ index + 1 }}</div>
                <div class="table__row">{{ item.fullName }}</div>
                <div class="table__row">{{ item.userName }}</div>
                <div class="table__row">{{ item.gender ? 'Nam' : 'Nữ' }}</div>
                <div class="table__row">{{ item.contact }}</div>
                <div class="table__row">
                  <span>{{ item.email }}</span>
                </div>
                <div class="table__row">{{ handleSplitRole(item.role.roleName) }}</div>
                <div class="table__row">
                  <span>{{ item.status ? 'Đang hoạt động' : 'Đã nghỉ việc' }}</span>
                </div>

                <div class="table__row align-items-center">
                  <div class="btn-group align-top">
                    <button class="btn__edit" data-toggle="modal" data-target="#myModal" @click="editClick(item)">
                      Edit
                    </button>
                    <button class="btn__delete" @click="remove(item.userId)">
                      Chuyển trạng thái
                    </button>
                  </div>
                </div>
              </div>
            </template>
          </tbody>
        </table> -->
        <b-table
          id="my-table"
          :items="listStaffSearch"
          :per-page="10"
          :current-page="currentPage"
          :fields="fields"
          small
        >
          <template #cell(userId)="data">
            <div v-if="currentPage > 1">
              {{ data.index + 1 === 10 ? `${currentPage}0` : `${currentPage - 1}${data.index + 1}` }}
            </div>
            <div v-else>
              {{ data.index + 1 }}
            </div>
          </template>
          <template #cell(gender)="data">
            {{ data.item.gender ? 'Nam' : 'Nữ' }}
          </template>
          <template #cell(role[roleName])="data">
            {{ handleSplitRole(data.item.role.roleName) }}
          </template>
          <template #cell(status)="data">
            {{ data.item.status ? 'Đang làm việc' : 'Đã nghỉ việc' }}
          </template>
          <template #cell(action)="data">
            <div class="table__row align-items-center">
              <div class="btn-group align-top">
                <button class="btn__edit" data-toggle="modal" data-target="#myModal" @click="editClick(data.item)">
                  Edit
                </button>
                <button class="btn__delete" @click="remove(data.item.userId)">
                  Chuyển trạng thái
                </button>
              </div>
            </div>
          </template>
          <template v-if="isLoading" #table-busy>
            <div class="text-center text-danger my-2">
              <div class="loading">
                <Loading />
              </div>
            </div>
          </template>
        </b-table>
      </div>

      <!-- Paging -->
      <section class="pagination__wrap">
        <div class="pagination__list">
          <b-pagination
            v-model="currentPage"
            :total-rows="totalStaff"
            :per-page="10"
            aria-controls="my-table"
          ></b-pagination>
        </div>
      </section>
    </div>

    <AddUserModal ref="addUserModalRef" :userId="userId" @doneAdd="onAdded" @doneUpdate="onUpdated" />
  </div>
</template>

<script>
import { staffManagementService } from '@/services';
import { filter } from 'lodash';
import AddUserModal from '../modals/AddUserModal/index.vue';
import Loading from '@/components/common/Loading/index.vue';
// import { mapState, mapMutations } from 'vue'
import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';
// import JwPagination from 'jw-vue-pagination';

Vue.use(VueToast, { position: 'top' });

export default {
  name: 'StaffManagement',

  components: {
    AddUserModal,
    Loading,
    // JwPagination
  },

  data() {
    return {
      isLoading: false,
      searchText: '',
      listStaff: [],
      userId: null,
      formUser: {
        fullName: '',
        profileImage: '',
        userName: '',
        password: '',
        contact: '',
        email: '',
        gender: '',
        role: {},
      },
      pageOfItems: [],
      currentPage: 1,
      fields: [
        { key: 'userId', label: 'ID' },
        { key: 'fullName', label: 'Họ và tên' },
        { key: 'userName', label: 'Tên tài khoản' },
        { key: 'gender', label: 'Giới tính' },
        { key: 'contact', label: 'SĐT' },
        { key: 'email', label: 'Email' },
        { key: 'role[roleName]', label: 'Role' },
        { key: 'status', label: 'Trạng thái' },
        { key: 'action', label: 'Action' }
      ]
    };
  },

  computed: {
    listStaffSearch() {
      if (this.searchText) {
        return this.listStaff.filter((item) => {
          return this.searchText
            .toLowerCase()
            .split(' ')
            .every((v) => item.userName.toLowerCase().includes(v));
        });
      } else {
        return this.listStaff;
      }
    },

    totalStaff() {
      return this.listStaff.length;
    },
  },

  mounted() {
    this.getListUser();
  },

  methods: {
    removeKeyword() {
      this.searchText = '';
    },

    handleShowAddUserModal() {
      this.$refs.addUserModalRef.show();
    },

    onAdded(val) {
      if (val) {
        this.getListUser();
      }
    },

    onUpdated(val) {
      if (val) {
        this.getListUser();
      }
    },

    onChangePage(val) {
      this.pageOfItems = val;
    },

    editClick(val) {
      this.userId = val.userId;
      this.$refs.addUserModalRef.show('update');
    },

    remove(val) {
      this.deleteUser(val);
    },

    handleSplitRole(str) {
      return str.slice(5);
    },

    async getListUser() {
      this.isLoading = true;
      const res = await staffManagementService
        .getListUser({
          headers: {
            Authorization: this.$auth.$storage._state['_token.local'],
          },
        })
        .finally(() => {
          this.isLoading = false;
        });

      if (res.success) {
        const resData = res.data;
        filter(resData, (item) => item.role.roleName === 'ROLE_ADMIN');
        this.listStaff = resData;
      }
    },

    deleteUser(userId) {
      // const res = await staffManagementService.deleteUser(userId);

      // if (res.status) {
      //   console.log('success');
      //   this.getListUser();
      // }
      this.$confirmPopup.open({
        title: 'Xác nhận',
        message: 'Bạn có chắc muốn đổi trạng thái tài khoản này không?',
        confirmText: 'Đổi',

        confirmed: async () => {
          const res = await staffManagementService.deleteUser(userId, {
            headers: {
              Authorization: this.$auth.$storage._state['_token.local'],
            },
          });

          if (res.success) {
            this.getListUser();
            Vue.$toast.success('Đổi trạng thái thành công');
          }
        },
      });
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
