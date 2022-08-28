<template>
  <b-modal
    ref="changePass"
    centered
    title="Đổi mật khẩu"
    body-class="modal__body"
    content-class="modal__content"
    scrollable
    hide-footer
    @hidden="handleHideModal"
  >
    <div class="changePass">
      <div class="change-pwd-container">
        <label class="change-pwd-container__label">
          Nhập mật khẩu cũ:
          <input id="oldPwd" v-model="oldPass" class="change-pwd-container__input" type="password" />
        </label>
        <label class="change-pwd-container__label">
          Nhập mật khẩu mới:
          <input id="newPwd" v-model="newPass" class="change-pwd-container__input" type="password" />
        </label>
        <label class="change-pwd-container__label">
          Xác nhận mật khẩu mới:
          <input id="cfNewPwd" v-model="confirmNewPass" class="change-pwd-container__input" type="password" />
        </label>
        <button class="change-pwd-container__btn" @click="confirmNewPassword()">
          Đổi mật khẩu
        </button>
      </div>
    </div>
  </b-modal>
</template>

<script>
import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';
import { authService } from '@/services';
import { mapGetters } from 'vuex';

Vue.use(VueToast, { position: 'top' });
export default {
  name: 'ChangePassModal',

  components: {
  },

  data() {
    return {
      userId: null,
      oldPass: '',
      newPass: '',
      confirmNewPass: '',
      wrongOldPass: false,
    };
  },

  computed: {
    ...mapGetters(['isAuthenticated', 'loggedInUser'])
  },

  methods: {
    show() {
      this.$refs.changePass.show();
    },

    handleHideModal() {
      this.$refs.changePass.hide();
    },

    validator() {
      if (!this.oldPass) {
        Vue.$toast.error('Vui lòng nhập mật khẩu cũ');
        document.getElementById('oldPwd').focus();
        return false;
      }

      if (this.wrongOldPass) {
        Vue.$toast.error('Mật khẩu cũ không đúng');
        document.getElementById('oldPwd').focus();
        return false;
      }

      if (!this.newPass) {
        Vue.$toast.error('Vui lòng nhập mật khẩu mới');
        document.getElementById('newPwd').focus();
        return false;
      }

      const regexPass = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;
      if (!regexPass.test(this.newPass)) {
        Vue.$toast.error('Mật khẩu phải chứa ít nhất 8 kí tự bao gồm 1 kí tự số, 1 chữ in hoa và 1 chữ in thường');
        this.newPass = '';
        document.getElementById('newPwd').focus();
        return false;
      }

      if (!this.confirmNewPass) {
        Vue.$toast.error('Vui lòng nhập lại mật khẩu mới');
        document.getElementById('cfNewPwd').focus();
        return false;
      }

      if (this.confirmNewPass !== this.newPass) {
        Vue.$toast.error('Mật khẩu không khớp');
        document.getElementById('cfNewPwd').focus();
        return false;
      }

      return true;
    },

    confirmNewPassword() {
      if (!this.validator()) {
        return;
      }
      const reqParams = {
        userId: this.loggedInUser.user.id,
        oldPassword: this.oldPass,
        newPassword: this.newPass,
        confirmNewPassword: this.confirmNewPass,
      };

      this.changePass(reqParams);

    },

    async changePass(reqParams) {
      this.wrongOldPass = false;
      const res = await authService.changePassword(reqParams, {
          headers: {
            Authorization: this.$auth.$storage._state['_token.local'],
          },
        }).catch((e) => {
          if (e.response.data.message === 'Old Password incorrect') {
            this.wrongOldPass = true;
          }
        });

      if (res.success) {
        Vue.$toast.success('Đổi mật khẩu thành công');
        this.handleHideModal();
      }
    }

  }
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
