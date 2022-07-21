<template>
  <div>
    <nav class="navbar navbar-expand-sm bg-success">
      <div class="header-logo">
        <nuxt-link to="/web" class="navbar-brand d-flex">MLD Restaurant</nuxt-link>
      </div>
      <button
        class="navbar-toggler border-success"
        type="button"
        data-toggle="collapse"
        data-target="#actionNav"
        aria-controls="actionNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
      </button>
      <div>
        <ul id="actionNav2" class="navbar-nav collapse navbar-collapse">
          <li>
            <div v-if="isAuthenticated" v-click-outside="closeManageAccount" class="account-management__dropdown">
              <div class="account-management__display d-flex align-items-center" @click="showManageAccount">
                <div class="account-username px-2">
                  {{ loggedInUser.user.username }}
                </div>
                <div class="arrow-icon">
                  <ArrowDown :current-color="'#fff'" />
                </div>
              </div>
              <div v-if="isShowManagementAccount" class="account-management__dropdown-menu text-left py-1">
                <div v-if="loggedInUser.user.roleName === 'ROLE_ADMIN'" class="dropdown-menu__item">
                  <nuxt-link class="w-100 h-100 d-flex align-items-center text-nowrap" to="/quan-ly-tai-khoan">
                    <span class="icon mr-2"><manage-account-icon :width="21" :height="21" /></span> Quản lý tài khoản
                  </nuxt-link>
                </div>
                <div class="dropdown-menu__item">
                  <nuxt-link class="w-100 h-100 d-flex align-items-center text-nowrap" to="/thong-tin-tai-khoan">
                    <span class="icon mr-2"><manage-account-icon :width="21" :height="21" /></span> Thông tin tài khoản
                  </nuxt-link>
                </div>
                <div class="dropdown-menu__item" @click="logoutUser()">
                  <i class="icon bi bi-box-arrow-in-left mr-2"></i><span class="sign-out">Đăng xuất</span>
                </div>
              </div>
            </div>
          </li>

          <li
            :class="{ 'nav-item-active': false }"
            class="nav-item menuItem"
            style="margin-left: 32px"
          >
            <nuxt-link class="nav-link" to="/bao-cao">
              <MenuDashboardIcon />
              <span class="is-hide menuItem__title">Báo cáo</span>
            </nuxt-link>
          </li>
          <li
            :class="{ 'nav-item-active': false }"
            class="nav-item menuItem"
          >
            <nuxt-link class="nav-link" to="/thuc-don">
              <MenuDashboardIcon />
              <span class="is-hide menuItem__title">Menu</span>
            </nuxt-link>
          </li>
          <li
            :class="{ 'nav-item-active': false }"
            class="nav-item menuItem"
          >
            <nuxt-link class="nav-link" to="/ban-an">
              <MenuDashboardIcon />
              <span class="is-hide menuItem__title">Bàn ăn</span>
            </nuxt-link>
          </li>
          <li
            :class="{ 'nav-item-active': false }"
            class="nav-item menuItem"
          >
            <nuxt-link class="nav-link" to="/don-hang">
              <MenuOrder />
              <span class="is-hide menuItem__title">Order</span>
            </nuxt-link>
          </li>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
import Vue from 'vue';
import { mapGetters } from 'vuex';
import vClickOutside from 'v-click-outside';
import { ArrowDown, ManageAccountIcon, MenuDashboardIcon, MenuOrder } from '@/components/CustomIcon';
Vue.use(vClickOutside);
export default {
  name: 'DefaultHeader',

  components: {
    ArrowDown,
    ManageAccountIcon,
    MenuDashboardIcon,
    MenuOrder,
  },

  data() {
    return {
      isShowManagementAccount: false,
    };
  },

  computed: {
    ...mapGetters(['isAuthenticated', 'loggedInUser'])
  },

  methods: {
    showManageAccount() {
      this.isShowManagementAccount = !this.isShowManagementAccount;
    },

    closeManageAccount() {
      this.isShowManagementAccount = false;
    },

    async logoutUser() {
      await this.$auth.logout();
    }
  }
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
