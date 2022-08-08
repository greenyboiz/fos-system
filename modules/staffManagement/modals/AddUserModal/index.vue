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
          <input id="fullname" v-model="formUser.fullName" type="text" placeholder="Nhập tên người dùng" />
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
        <div v-if="!modalTitle" class="info-staff__item">
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
                :keyValue="`${gender.id}g`"
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
        <div v-if="modalTitle" class="info-staff__item">
          <label for="status">Trạng thái:</label>
          <div style="width: 100%">{{ statusSelected === 1 ? 'Đang làm việc' : 'Đã nghỉ việc' }}</div>
          <!-- <div class="d-flex" style="width: 100%"> -->
            <!-- <div v-for="status in statusType" :key="status.id">
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
            </div> -->
          <!-- </div> -->
        </div>
      </div>
    </div>
    <div style="padding: 10px">
      <button class="staff__btn" :disabled="isLoading" @click="handleSave()">
        <!-- <Loading v-if="isLoading" color="white" /> -->
        <span v-if="!modalTitle">Thêm mới</span>
        <span v-else>Cập nhật</span>
      </button>
    </div>
  </b-modal>
</template>

<script>
import ImageOrDefault from '@/components/common/ImageOrDefault/index.vue';
import CustomCheckbox from '@/components/common/CustomCheckbox/index.vue';
import { staffManagementService } from '@/services';
import { toNumber } from 'lodash';

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
        { id: 0, name: 'Nữ' },
        { id: 1, name: 'Nam' },
      ],
      genderSelected: 1,
      roleSelected: 'ROLE_ADMIN',
      roleType: [
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
      if (title) {
        this.modalTitle = title;
        this.getUserById();
      }
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
      this.avatar = '';
      this.$refs.addUser.hide();
    },

    removeAscent(str) {
      if (str === null || str === undefined) return str;

      str = str.toLowerCase();
      str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, 'a');
      str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, 'e');
      str = str.replace(/ì|í|ị|ỉ|ĩ/g, 'i');
      str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, 'o');
      str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, 'u');
      str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, 'y');
      str = str.replace(/đ/g, 'd');
      return str;
    },

    validator() {
      const regexName = /^([a-zA-Z ]){2,}$/;
      if (!this.formUser.fullName) {
        Vue.$toast.error('Bạn chưa nhập Họ và tên');
        document.getElementById('fullname').focus();
        return false;
      }

      if (!regexName.test(this.removeAscent(this.formUser.fullName))) {
        Vue.$toast.error('Họ và tên phải là chữ cái và có đội dài từ 2 kí tự');
        this.formUser.fullName = '';
        document.getElementById('fullname').focus();
        return;
      }

      if (!this.formUser.userName) {
        Vue.$toast.error('Bạn chưa nhập Tên tài khoản');
        document.getElementById('username').focus();
        return false;
      }

      const regexUsername = /^([a-zA-Z0-9]){2,30}$/;

      if (!regexUsername.test(this.formUser.userName)) {
        Vue.$toast.error('Tên tài khoản không được chứa kí tự đặc biệt và có độ dài từ 2-30 kí tự');
        this.formUser.userName = '';
        document.getElementById('username').focus();
        return;
      }

      if (!this.formUser.contact) {
        Vue.$toast.error('Bạn chưa nhập Số điện thoại');
        document.getElementById('phone').focus();
        return false;
      }

      const regexPhone = /(84|0[3|5|7|8|9])+([0-9]{8})\b/g;
      if (!regexPhone.test(this.formUser.contact)) {
        Vue.$toast.error('Bạn chưa nhập đúng định dạng số điện thoại');
        this.formUser.contact = '';
        document.getElementById('phone').focus();
        return false;
      }

      if (!this.formUser.email) {
        Vue.$toast.error('Bạn chưa nhập Email');
        document.getElementById('email').focus();
        return false;
      }

      const regexEmail = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;

      if (!regexEmail.test(this.formUser.email)) {
        Vue.$toast.error('Bạn chưa nhập đúng định dạng email');
        this.formUser.email = '';
        document.getElementById('email').focus();
        return false;
      }

      if (!this.formUser.password) {
        Vue.$toast.error('Bạn chưa nhập Mật khẩu');
        document.getElementById('pass').focus();
        return false;
      }

      const regexPass = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;
      if (!regexPass.test(this.formUser.password)) {
        Vue.$toast.error('Mật khẩu phải chứa ít nhất 8 kí tự bao gồm 1 kí tự số, 1 chữ in hoa và 1 chữ in thường');
        this.formUser.password = '';
        document.getElementById('pass').focus();
        return false;
      }
      if (this.genderSelected === '') {
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
        .createUploadWidget({ cloud_name: 'dk7clbldt', upload_preset: 'xm84zfhu' }, (error, result) => {
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

      if (res.success) {
        this.isDone = true;
        this.$emit('doneAdd', this.isDone);
        Vue.$toast.success('Thêm nhân viên thành công');
      }
    },

    async updateUser(requestParams) {
      const res = await staffManagementService.updateUser(requestParams, {
        headers: {
          Authorization: this.$auth.$storage._state['_token.local'],
        },
      });

      if (res.success) {
        this.isDone = true;
        this.$emit('doneUpdate', this.isDone);
        Vue.$toast.success('Cập nhật nhân viên thành công');
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
        this.statusSelected = user.status ? 1 : 0;
        this.roleSelected = user.role.roleName;
        this.genderSelected = user.gender ? 1 : 2;
      }
    },

    handleSave() {
      if (!this.validator()) {
        return;
      }

      const requestParams = {
        profileImage: this.avatar,
        fullName: this.formUser.fullName.trim(),
        userName: this.formUser.userName.trim(),
        // password: this.formUser.password,
        contact: this.formUser.contact.trim(),
        email: this.formUser.email.trim(),
        gender: toNumber(this.genderSelected.toString()),
        role: {
          roleId: this.roleIdSelected,
          roleName: this.roleSelected,
        },
        status: this.statusSelected,
      };

      if (this.modalTitle) {
        requestParams.userId = this.userId;
        this.updateUser(requestParams);
      } else {
        requestParams.password = this.formUser.password.trim();
        this.addUser(requestParams);
      }

      this.handleHideModal();
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
