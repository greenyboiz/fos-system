<template>
  <div class="menuManagement">
    <!-- Top -->
    <div class="headline">
      <div class="headline__top">
        <div class="headline__title">Quản lý nhân viên</div>

        <div class="headline__right">
          <button class="btn__add" @click="handleShowAddUserModal()">Thêm người dùng</button>

          <div class="searchArea">
            <div class="searchBox d-flex align-items-center">
              <img class="searchBox__icon" src="~/assets/icons/search_gray_icon.png" />
              <input v-model="searchText" class="searchBox__input" placeholder="Tìm kiếm thông tin người dùng" />
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
      <div class="table">
        <table class="table__wrapper">
          <div class="table__head">
            <div class="table__col">ID</div>
            <div class="table__col">Họ và tên</div>
            <div class="table__col">Tên tài khoản</div>
            <div class="table__col">Mật khẩu</div>
            <div class="table__col">Giới tính</div>
            <div class="table__col">SĐT</div>
            <div class="table__col">Email</div>
            <div class="table__col">Role</div>
            <div class="table__col">Action</div>
          </div>

          <tbody class="table__bot">
            <template v-if="isLoading">
              <div class="loading">
                <Loading />
              </div>
            </template>

            <template v-else>
              <div v-for="item in listStaffSearch" :key="'staff' + item.userId" class="table__body">
                <div class="table__row">{{ item.userId }}</div>
                <div class="table__row">{{ item.fullName }}</div>
                <div class="table__row">{{ item.userName }}</div>
                <div class="table__row">
                  <span>{{ item.password }}</span>
                </div>
                <div class="table__row">{{ item.gender ? 'Nam' : 'Nữ' }}</div>
                <div class="table__row">{{ item.contact }}</div>
                <div class="table__row">
                  <span>{{ item.email }}</span>
                </div>
                <div class="table__row">{{ item.role.roleName }}</div>

                <div class="table__row align-items-center">
                  <div class="btn-group align-top">
                    <button class="btn__edit" data-toggle="modal" data-target="#myModal" @click="editClick(item)">
                      Edit
                    </button>
                    <button class="btn__delete" @click="remove(item.userId)">
                      <img src="@/assets/icons/delete.png" alt="" />
                    </button>
                  </div>
                </div>
              </div>
            </template>
          </tbody>
        </table>
      </div>

      <!-- Paging -->
      <div class="pagination">
        <section class="pagination__wrap">
          <div class="pagination__list">
            <button class="pagination__button active">1</button>
            <button class="pagination__button">2</button>
          </div>
        </section>
      </div>
    </div>

    <AddUserModal ref="addUserModalRef" :userId="userId" @doneAdd="onAdded" @doneUpdate="onUpdated" />
  </div>
</template>

<script>
import { staffManagementService } from '@/services';
import AddUserModal from '../modals/AddUserModal/index.vue';
import Loading from '@/components/common/Loading/index.vue';
// import { mapState, mapMutations } from 'vue'

export default {
  name: 'StaffManagement',

  components: {
    AddUserModal,
    Loading,
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

    editClick(val) {
      this.userId = val.userId;
      this.$refs.addUserModalRef.show('update');
    },

    remove(val) {
      this.deleteUser(val);
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
        this.listStaff = res.data;
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
        message: 'Bạn có chắc muốn xóa tài khoản này không?',
        confirmText: 'Xóa',

        confirmed: async () => {
          const res = await staffManagementService.deleteUser(userId, {
            headers: {
              Authorization: this.$auth.$storage._state['_token.local'],
            },
          });

          if (res.success) {
            this.getListUser();
          }
        },
      });
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
