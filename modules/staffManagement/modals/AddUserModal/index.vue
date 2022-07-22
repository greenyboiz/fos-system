<template>
  <b-modal
    ref="addUser"
    centered
    :title="modalTitle ? 'Sửa người dùng' : 'Thêm người dùng'"
    body-class="modal__body"
    content-class="modal__content"
    scrollable
    hide-footer
    @hidden="handleHideModal"
  >
    <div class="info-staff">
      <div class="info-staff__avatar" @click="chooseAvatar()">
        <ImageOrDefault class="info-staff__img" :src="avatar" alt="avatar" />
        <div class="info-staff__add">+</div>
      </div>
      <input ref="avatarRef" style="display: none" type="file" accept="image/*" @change="handleAddAvatar" />
      <div class="info-staff__detail">
        <div class="info-staff__item">
          <label for="name">Họ và Tên:</label>
          <input id="name" v-model="formUser.fullName" type="text" placeholder="Nhập tên người dùng" />
        </div>
        <div class="info-staff__item">
          <label for="username">Tên tài khoản:</label>
          <input id="username" v-model="formUser.userName" type="text" placeholder="Nhập tên tài khoản" />
        </div>
        <div class="info-staff__item">
          <label for="phone">SĐT:</label>
          <input id="phone" v-model="formUser.contact" type="text" placeholder="Nhập SĐT" />
        </div>
        <div class="info-staff__item">
          <label for="email">Email:</label>
          <input id="email" v-model="formUser.email" type="text" placeholder="Nhập email" />
        </div>
        <div class="info-staff__item">
          <label for="pass">Mật khẩu:</label>
          <input
            id="pass"
            v-model="formUser.password"
            :type="showPassword ? 'text' : 'password'"
            placeholder="Nhập mật khẩu"
          />
          <img src="~/assets/icons/eye.png" alt="" @click="handleShowPassword()" />
        </div>
        <div class="info-staff__item">
          <label for="gender">Giới tính:</label>
          <div class="d-flex" style="width: 100%">
            <div v-for="gender in genderType" :key="gender.id">
              <CustomCheckbox
                v-model="genderSelected"
                class="mr-2"
                shape="circle"
                type="radio"
                styleCheck="dot"
                :keyValue="gender.id"
                :inputValue="gender.name"
                :customLabel="true"
              >
                <template slot="custom-label"> {{ gender.name }} </template>
              </CustomCheckbox>
            </div>
          </div>
        </div>
        <div class="info-staff__item">
          <label for="role">Role:</label>
          <div class="d-flex" style="width: 100%">
            <div v-for="role in roleType" :key="role.roleId">
              <CustomCheckbox
                v-model="roleSelected"
                class="mr-2"
                shape="circle"
                type="radio"
                styleCheck="dot"
                :keyValue="`${role.roleId}r`"
                :inputValue="role.roleName"
                :customLabel="true"
              >
                <template slot="custom-label"> {{ role.roleName }} </template>
              </CustomCheckbox>
            </div>
          </div>
        </div>
        <div class="info-staff__item">
          <label for="status">Trạng thái:</label>
          <div class="d-flex" style="width: 100%">
            <div v-for="status in statusType" :key="status.id">
              <CustomCheckbox
                v-model="statusSelected"
                class="mr-2"
                shape="circle"
                type="radio"
                styleCheck="dot"
                :keyValue="`${status.id}s`"
                :inputValue="status.id"
                :customLabel="true"
                @input="handleSelectedStatus"
              >
                <template slot="custom-label"> {{ status.name }} </template>
              </CustomCheckbox>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div style="padding: 10px">
      <button class="staff__btn" :disabled="isLoading" @click="handleSave()">
        <!-- <Loading v-if="isLoading" color="white" /> -->
        <span>Xong</span>
      </button>
    </div>
  </b-modal>
</template>

<script>
import ImageOrDefault from '@/components/common/ImageOrDefault/index.vue';
import CustomCheckbox from '@/components/common/CustomCheckbox/index.vue';
import { staffManagementService } from '@/services';

import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

Vue.use(VueToast, { position: 'top' });
export default {
  name: 'AddUserModal',

  components: {
    ImageOrDefault,
    CustomCheckbox,
  },

  props: {
    userId: {
      type: Number,
      default: 0,
    },
  },

  data() {
    return {
      formUser: {
        fullName: '',
        userName: '',
        password: '',
        contact: '',
        email: '',
        gender: '',
        role: {
          roleId: null,
          roleName: '',
        },
        status: null,
      },
      modalTitle: '',
      showPassword: false,
      avatar: null,
      genderType: [
        { id: 1, name: 'nam' },
        { id: 2, name: 'nu' },
      ],
      genderSelected: '',
      roleSelected: '',
      roleType: [
        { roleId: 1, roleName: 'ADMIN' },
        { roleId: 2, roleName: 'STAFF' },
        { roleId: 3, roleName: 'CHEF' },
        { roleId: 4, roleName: 'CUSTOMER' },
      ],
      statusSelected: null,
      statusType: [
        { id: 0, name: 'Đang làm việc' },
        { id: 1, name: 'Đã nghỉ' },
      ],
      isLoading: false,
      isDone: false,
    };
  },

  computed: {
    roleIdSelected() {
      if (this.roleSelected === 'ADMIN') {
        return 1;
      } else if (this.roleSelected === 'STAFF') {
        return 2;
      } else if (this.roleSelected === 'CHEF') {
        return 3;
      } else if (this.roleSelected === 'CUSTOMER') {
        return 4;
      } else {
        return 0;
      }
    },
  },

  watch: {
    userId() {
      this.getUserById();
    },
  },

  methods: {
    show(title) {
      this.modalTitle = title;
      this.$refs.addUser.show();
    },

    handleHideModal() {
      this.formUser = {
        fullName: '',
        userName: '',
        password: '',
        contact: '',
        email: '',
        gender: '',
        role: {
          roleId: null,
          roleName: '',
        },
        status: null,
      };
      this.roleSelected = '';
      this.genderSelected = '';
      this.statusSelected = '';
      this.$refs.addUser.hide();
    },

    validator() {
      if (!this.formUser.fullName) {
        Vue.$toast.error('Bạn chưa nhập Họ và tên');
        return false;
      }

      if (!this.formUser.userName) {
        this.$toast.error('Bạn chưa nhập Tên tài khoản');
        return false;
      }

      if (!this.formUser.password) {
        this.$toast.error('Bạn chưa nhập Mật khẩu');
        return false;
      }

      if (!this.formUser.contact) {
        this.$toast.error('Bạn chưa nhập Số điện thoại');
        return false;
      }

      if (!this.formUser.contact.match(/(84|0[3|5|7|8|9])+([0-9]{8})\b/g)) {
        this.$toast.error('Bạn chưa nhập đúng định dạng');
        return false;
      }

      if (!this.formUser.email) {
        this.$toast.error('Bạn chưa nhập Email');
        return false;
      }

      if (!this.genderSelected) {
        this.$toast.error('Bạn chưa chọn giới tính');
        return false;
      }

      if (!this.roleSelected) {
        this.$toast.error('Bạn chưa phân quyền tài khoản');
        return false;
      }

      if (!this.statusSelected) {
        this.$toast.error('Bạn chưa chọn trạng thái');
        return false;
      }

      return true;
    },

    chooseAvatar() {
      this.$refs.avatarRef.click();
    },

    handleSelectedStatus() {
      // console.log(this.statusSelected);
    },

    handleAddAvatar() {
      const file = this.$refs.avatarRef.files;
      if (!file.length) return;

      // this.avatar = file[0];
      this.avatar = URL.createObjectURL(file[0]);
      console.log(this.avatar);
      // e.target.value = '';
    },

    handleShowPassword() {
      this.showPassword = !this.showPassword;
    },

    async addUser() {
      this.formUser.gender = this.genderSelected;
      this.formUser.status = this.statusSelected;
      this.formUser.role.roleId = this.roleIdSelected;
      this.formUser.role.roleName = this.roleSelected;
      const requestParams = this.formUser;
      const formData = new FormData();
      formData.set('file', this.$refs.avatarRef.files[0]);
      formData.set('users', requestParams);

      const res = await staffManagementService.addUser(formData, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.status === 200) {
        this.isDone = true;
        this.$emit('doneAdd', this.isDone);
      } else {
        this.isDone = false;
      }
    },

    async updateUser() {
      this.formUser.gender = this.genderSelected;
      this.formUser.status = this.statusSelected;
      this.formUser.role.roleId = this.roleIdSelected;
      this.formUser.role.roleName = this.roleSelected;
      this.formUser.userId = this.userId;
      const requestParams = this.formUser;

      const res = await staffManagementService.updateUser(
        {
          file: null,
          users: requestParams,
        },
        {
          headers: {
            Authorization: this.$auth.$storage._state['_token.local'],
          },
        }
      );

      if (res.status === 200) {
        this.isDone = true;
        this.$emit('doneUpdate', this.isDone);
      } else {
        this.isDone = false;
      }
    },

    async getUserById() {
      const res = await staffManagementService.getUserById(this.userId, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        const user = res.data;
        this.formUser.fullName = user.fullName;
        this.formUser.userName = user.userName;
        this.formUser.password = user.password;
        this.formUser.contact = user.contact;
        this.avatar = user.profileImage;
        this.formUser.email = user.email;
        this.formUser.gender = user.gender;
        this.statusSelected = user.status;
        this.roleSelected = user.role.roleName;
        this.genderSelected = user.gender;
      }
    },

    handleSave() {
      console.log(this.formUser);
      if (!this.validator()) {
        return;
      }

      if (this.modalTitle) {
        this.updateUser();
      } else {
        this.addUser();
      }

      // this.handleHideModal();
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
