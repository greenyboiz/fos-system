<template>
  <div class="staffManagement">
    <div class="headline">
      <div class="headline__left">
        <div class="headline__title">Quản lý nhân viên</div>
        <div class="headline__count">{{ totalStaff }} nhân viên</div>
      </div>
      <div class="headline__right">
        <div class="searchArea">
          <div class="searchBox d-flex align-items-center">
            <img
              class="searchBox__icon"
              src="~/assets/icons/search_gray_icon.png"
            />
            <input
              v-model="searchText"
              class="searchBox__input"
              placeholder="Tìm kiếm thông tin nhân viên"
            />
            <div
              v-if="searchText"
              class="cursor-pointer"
              @click="removeKeyword()"
            >
              <img
                class="searchBox__remove"
                src="~/assets/icons/remove_search.svg"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="staffManagement__list">
      <div class="staffManagement__list--title">Danh sách nhân viên</div>
      <div class="staffManagement__list--table">
        <table class="table table-bordered table-hover">
          <thead>
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Họ và tên</th>
              <th scope="col">Tên tài khoản</th>
              <th scope="col">Mật khẩu</th>
              <th scope="col">Giới tính</th>
              <th scope="col">SĐT</th>
              <th scope="col">Email</th>
              <th scope="col">Role</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in listStaffSearch" :key="item.id" class="tw-cursor-pointer">
              <th scope="row">{{ item.userId }}</th>
              <td>{{ item.fullName }}</td>
              <td>{{ item.userName }}</td>
              <td>{{ item.password }}</td>
              <td>{{ item.gender }}</td>
              <td>{{ item.contact }}</td>
              <td>{{ item.email }}</td>
              <td>{{ item.role.roleName }}</td>
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
                    @click="remove(item.userId)"
                  >
                    Delete
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <nav aria-label="...">
      <ul class="pagination">
        <li class="page-item disabled">
          <span class="page-link">Previous</span>
        </li>
        <li class="page-item active" aria-current="page"><a class="page-link" href="#">1</a></li>
        <li class="page-item">
          <span class="page-link">2</span>
        </li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>
        <li class="page-item">
          <a class="page-link" href="#">Next</a>
        </li>
      </ul>
    </nav>
    <div class="add-button">
      <button class="btn btn-success" @click="handleShowAddUserModal()">Thêm người dùng</button>
    </div>
    <AddUserModal ref="addUserModalRef" :userId="userId" @doneAdd="onAdded" @doneUpdate="onUpdated" />
  </div>
</template>

<script>
import { staffManagementService } from '@/services';
import AddUserModal from '../modals/AddUserModal/index.vue';
// import { mapState, mapMutations } from 'vue'
export default {
  name: 'StaffManagement',

  components: {
    AddUserModal
  },

  data() {
    return {
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
    listStaffSearch(){
      if(this.searchText){
      return this.listStaff.filter((item) => {
        return this.searchText.toLowerCase().split(' ').every(v => item.name.toLowerCase().includes(v));
      });
      }else{
        return this.listStaff;
      }
    },

    totalStaff() {
      return this.listStaff.length;
    }
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
      const res = await staffManagementService.getListUser();

      this.listStaff = res.data;
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
          const res = await staffManagementService.deleteUser(userId);

          if (res.status === 200) {
            this.getListUser();
          }
        }
      });
    }
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
