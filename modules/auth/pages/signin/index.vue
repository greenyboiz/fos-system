<template>
  <div class="container">
    <div class="login-screen">
      <div class="header">
        <!-- <img alt="" class="logo" src="~assets/images/moshop.png" /> -->
      </div>
      <Notification v-if="error" :message="error" />
      <form class="login-container" method="post" @submit.prevent="login">
        <div class="login-form">
          <div class="login-text text-center">Đăng nhập</div>
          <div class="form-group mb-0">
            <label v-show="username.length > 0" class="login-input-text" for="tel">
              Tên tài khoản
            </label>
            <input
              id="tel"
              v-model="username"
              :class="{ 'm-0': username.length > 0 }"
              class="form-control"
              name="username"
              placeholder="Tên tài khoản"
              type="text"
            />
          </div>
          <div class="form-group pwd-div">
            <label v-show="username.length > 0" class="login-input-text" for="pwd">Mật khẩu</label>
            <input
              id="pwd"
              v-model="password"
              :class="{ 'm-0': username.length > 0 }"
              autocomplete="on"
              class="form-control"
              name="password"
              placeholder="Mật khẩu"
              type="password"
            />
          </div>
          <div class="login-form__forget-pwd clickable-font">
            <p v-b-modal.forget-pwd-modal>Quên mật khẩu?</p>
          </div>
          <div class="center-items">
            <button
              v-if="!startLogin"
              :disabled="password.length === 0 || username.length === 0"
              class="login-form__btn-login mx-auto"
              type="submit"
            >
              Đăng nhập
            </button>
            <dashboard-loading v-else />
          </div>
        </div>
      </form>
    </div>
    <b-modal id="forget-pwd-modal" centered hide-footer title="Quên mật khẩu">
      <div class="forget-pwd-container">
        <label class="forget-pwd-container__label">
          Vui lòng điền email của bạn:
          <input v-model="forgetPwdEmail" class="forget-pwd-container__input" type="text" />
        </label>
        <button :disabled="forgetPwdEmail.length === 0" class="forget-pwd-container__btn" @click="sendForgetPasswordRq">
          Gửi yêu cầu
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script>
import Notification from '../../components/Notification.vue';
import { authService } from '@/services';
import auth from '~/middleware/auth';
export default {

  components: {
    Notification,
  },

  data() {
    return {
      isShowSignInModal: false,
      username: '',
      password: '',
      forgetPwdEmail: '',
      error: '',
      startLogin: false,
      isFinishRegister: true,
      registerStep: 0,
    };
  },
  methods: {
    validateEmail(email) {
      // eslint-disable-next-line max-len
      const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(String(email).toLowerCase());
    },

    sendForgetPasswordRq() {
      if (!this.validateEmail(this.forgetPwdEmail)) {
        this.$toast.error('Vui lòng điền đúng định dạng email!');
        this.forgetPwdEmail = '';
      }
    },

    getRegisterInfo() {
    },

    async getRegisterStep() {
    },

    async login() {
      const reqParams = {
        userName: this.username,
        password: this.password,
      };

      try {
        const res = await this.$auth.loginWith('local', {
          data: reqParams
        });
        const roleName = res.data.data.roleName;
        if (roleName === 'ROLE_ADMIN') {
          this.$router.push('/don-hang');
        } else if (roleName === 'ROLE_STAFF') {
          this.$router.push('/employee');
        } else if (roleName === 'ROLE_CHEF') {
          this.$router.push('/chef');
        }
      } catch (error) {
        this.error = 'Tên tài khoản hoặc mật khẩu không chính xác';
      }
    },
  },
};
</script>

<style scoped lang="scss" src="./styles.scss"></style>
