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
      <input
        id="avatarUser"
        ref="avatarRef"
        style="display: none"
        type="file"
        accept="image/*"
        @change="handleAddAvatar($event)"
      />
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
          <img class="info-staff__hide" src="~/assets/icons/eye.png" alt="" @click="handleShowPassword()" />
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
                :inputValue="gender.id"
                :customLabel="true"
              >
                <template slot="custom-label"> {{ gender.name }} </template>
              </CustomCheckbox>
            </div>
          </div>
        </div>
        <div class="info-staff__item">
          <label for="role">Role: </label>
          <div class="d-flex" style="width: 100%">
            <div v-for="role in roleType" :key="role.roleId">
              <CustomCheckbox
                v-model="roleSelected"
                class="mr-2"
                shape="circle"
                type="radio"
                styleCheck="dot"
                :keyValue="`${role.roleId}r`"
                :inputValue="`ROLE_${role.roleName}`"
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
        profileImage: '',
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
        { id: 1, name: 'Nam' },
        { id: 2, name: 'Nữ' },
      ],
      genderSelected: 1,
      roleSelected: 'ROLE_ADMIN',
      roleType: [
        { roleId: 1, roleName: 'ADMIN' },
        { roleId: 2, roleName: 'STAFF' },
        { roleId: 3, roleName: 'CHEF' },
        { roleId: 4, roleName: 'CUSTOMER' },
      ],
      statusSelected: 0,
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
      if (this.roleSelected === 'ROLE_ADMIN') {
        return 1;
      } else if (this.roleSelected === 'ROLE_STAFF') {
        return 2;
      } else if (this.roleSelected === 'ROLE_CHEF') {
        return 3;
      } else if (this.roleSelected === 'ROLE_CUSTOMER') {
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
        profileImage: '',
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
      this.statusSelected = null;
      this.$refs.addUser.hide();
    },

    validator() {
      if (!this.formUser.fullName) {
        Vue.$toast.error('Bạn chưa nhập Họ và tên');
        return false;
      }

      if (!this.formUser.userName) {
        Vue.$toast.error('Bạn chưa nhập Tên tài khoản');
        return false;
      }

      if (!this.formUser.password) {
        Vue.$toast.error('Bạn chưa nhập Mật khẩu');
        return false;
      }

      if (!this.formUser.contact) {
        Vue.$toast.error('Bạn chưa nhập Số điện thoại');
        return false;
      }

      if (!this.formUser.contact.match(/(84|0[3|5|7|8|9])+([0-9]{8})\b/g)) {
        Vue.$toast.error('Bạn chưa nhập đúng định dạng');
        return false;
      }

      if (!this.formUser.email) {
        Vue.$toast.error('Bạn chưa nhập Email');
        return false;
      }

      if (!this.genderSelected) {
        Vue.$toast.error('Bạn chưa chọn giới tính');
        return false;
      }

      if (!this.roleSelected) {
        Vue.$toast.error('Bạn chưa phân quyền tài khoản');
        return false;
      }

      if (this.statusSelected === '') {
        Vue.$toast.error('Bạn chưa chọn trạng thái');
        return false;
      }

      return true;
    },

    chooseAvatar() {
      window.cloudinary
        .createUploadWidget({ cloud_name: 'dk7clbldt', upload_preset: 'ml_default' }, (error, result) => {
          if (!error && result && result.event === 'success') {
            console.log('Done uploading..: ', result.info);
            this.avatar = result.info.url;
          }
        })
        .open();
    },

    handleSelectedStatus() {
      console.log(this.statusSelected);
    },

    // handleAddAvatar(e) {
    //   const file = this.$refs.avatarRef.files;
    //   if (!file.length) return;

    //   this.avatar = URL.createObjectURL(file[0]);
    //   // this.avatar = file[0];
    //   // console.log(this.avatar);
    //   // e.target.value = '';
    // },

    handleShowPassword() {
      this.showPassword = !this.showPassword;
    },

    async addUser(requestParams) {
      const res = await staffManagementService.addUser(requestParams, {
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

    async updateUser(requestParams) {
      const res = await staffManagementService.updateUser(requestParams, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

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
        this.genderSelected = parseInt(user.gender);
      }
    },

    handleSave() {
      if (!this.validator()) {
        return;
      }

      const requestParams = {
        profileImage: this.avatar,
        fullName: this.formUser.fullName,
        userName: this.formUser.userName,
        password: this.formUser.password,
        contact: this.formUser.contact,
        email: this.formUser.email,
        gender: this.genderSelected.toString(),
        role: {
          roleId: this.roleIdSelected,
          roleName: this.roleSelected,
        },
        status: this.statusSelected,
      };

      console.log(requestParams);

      if (this.modalTitle) {
        requestParams.id = this.userId;
        this.updateUser(requestParams);
      } else {
        this.addUser(requestParams);
      }

      this.handleHideModal();
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
