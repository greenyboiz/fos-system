<template>
  <div class="w-100">
    <template v-if="!isShowForgetPassword && !isShowVerification">
      <Notification v-if="error" :message="error" />
      <div class="login-section">
        <div class="section-heading">Đăng nhập</div>

        <div class="section-form">
          <div class="field-item">
            <div class="field-title">Tên tài khoản</div>
            <input placeholder="Nhập tên tài khoản" autofocus/>
            <!-- <template v-if="summited && !$v.username.email && !$v.username.phone">
              <span v-if="!$v.username.required" class="error">Số điện thoại hoặc email không được để trống</span>
              <span v-if="!$v.username.email && !$v.username.phone" class="error">Số điện thoại hoặc email sai định dạng</span>
            </template> -->
          </div>

          <div class="field-item">
            <div class="field-title">Mật khẩu</div>
            <div class="field-input">
              <input
                autocomplete="do-not-autofill"
                :type="!isShowPassword ? 'password' : 'text'"
                placeholder="Nhập mật khẩu"
              />
              <div class="field-input__action">
                <span class="forget-password pr-1" @click="handleForgetPassword">Quên mật khẩu?</span>
                <span class="eye-icon" @click="isShowPassword = !isShowPassword">
                  <EyeIcon v-if="isShowPassword"></EyeIcon>
                  <HideEyeIcon v-else></HideEyeIcon>
                </span>
              </div>
            </div>
            <!-- <template v-if="summited && $v.password.$error">
              <span v-if="!$v.password.required" class="error">Mật khẩu không được để trống</span>
            </template> -->
          </div>
        </div>

        <div class="section-footer">
          <button class="btn-continue">Đăng nhập</button>
        </div>
      </div>
    </template>
    <!-- <ForgetPassword
      v-if="isShowForgetPassword"
      :username="username"
      @onUpdatePasswordSuccess="handleUpdatePasswordSuccess"
      @onGoBack="handleUpdatePasswordSuccess"
    ></ForgetPassword>
    <InformationVerification
      v-if="isShowVerification"
      :formData="formData"
      @onGoBack="handleGoBack"
    ></InformationVerification> -->
  </div>
</template>

<script>
// import { required, minLength, email, sameAs } from 'vuelidate/lib/validators';
// import { customValidator } from '@/plugins/validators';

// import InformationVerification from '../Register/InformationVerification/index.vue';
// import ForgetPassword from '../ForgetPassword/index.vue';
import { EyeIcon, HideEyeIcon } from '@/modules/auth/icons';
import Notification from '../../components/Notification.vue';

// const { phone } = customValidator;

export default {

  components: { EyeIcon, HideEyeIcon, Notification },

  // eslint-disable-next-line vue/require-prop-types
  props: ['dataRegister'],

  data() {
    return {
      formData: {},
      username: '',
      password: '',
      isShowVerification: false,
      isShowPassword: false,
      isShowConfirmPassword: false,
      isShowForgetPassword: false,
      jwt: '',
      summited: false,
      isFinishRegister: true,
      error: '',
    };
  },

  // validations: {
  //   username: {
  //     required,
  //     phone,
  //     email
  //   },
  //   password: {
  //     required,
  //   },
  // },

  mounted() {
    // if (this.dataRegister.email || this.dataRegister.password) {
    //   this.username = this.dataRegister.email;
    //   this.password = this.dataRegister.password;
    // }
  },

  methods: {
    handleGoBack() {
      this.isShowVerification = false;
    },

    handleBack() {
      this.$emit('onGoBack');
    },

    handleOpenVerifyInformation() {
      this.isShowVerification = true;
    },

    handleForgetPassword() {
      this.isShowForgetPassword = !this.isShowForgetPassword;
    },

    handleUpdatePasswordSuccess() {
      this.isShowForgetPassword = false;
    },

    handleOpenRegister() {
      this.$emit('onOpenRegister');
    },
  },
};
</script>

<style scoped lang="scss" src="./styles.scss"></style>
