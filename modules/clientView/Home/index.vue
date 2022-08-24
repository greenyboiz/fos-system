<template>
  <div class="client-home">
    <div class="headline">
      <div class="headline__title">MLD Restaurent</div>
      <!-- <div class="headline__support" @click="handleShowSupportModal()">Support</div> -->
    </div>
    <div class="campaign">
      <VueSlickCarousel :arrows="false" :dots="true" :autoplay="true">
        <div class="campaign-item">
          <img src="@/assets/images/campaign.jpg" alt="" width="100%" />
        </div>
        <div class="campaign-item">
          <img src="@/assets/images/campaign.jpg" alt="" width="100%" />
        </div>
      </VueSlickCarousel>
    </div>
    <div class="customer">
      <div class="customer__item">
        <label for="fullname">Họ và tên:</label>
        <input id="fullname" v-model="formData.customer.fullName" type="text" placeholder="Nhập họ và tên" />
      </div>
      <div class="customer__item">
        <label for="phone">SĐT: <span style="color: red; margin-bottom: 4px">*</span></label>
        <input id="phone" v-model="formData.customer.contact" type="text" placeholder="Nhập SĐT" required />
      </div>
    </div>
    <div v-if="formData.customer.contact" class="getDish" @click="handleGetOrder()">
      <!-- eslint-disable-next-line max-len -->
      <!-- <nuxt-link :to="isValidate ? `/khach-hang/order/${orderId}` : `/khach-hang/table/${formData.qrCode.qrcodeId}`"> -->
        <button>Gọi món</button>
      <!-- </nuxt-link> -->
    </div>
    <!-- <div><button @click="sendMessage(1)">test socket</button></div> -->
    <SupportModal ref="supportModalRef" />
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
import VueSlickCarousel from 'vue-slick-carousel';
import { orderService, tableManagementService } from '@/services';
import { isEmpty } from 'lodash';
import SupportModal from '@/components/common/SupportModal/index.vue';
import SockJs from 'sockjs-client';
import StompClient from 'webstomp-client';

import Vue from 'vue';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

Vue.use(VueToast, { position: 'top' });
export default {
  name: 'Page',

  components: {
    VueSlickCarousel,
    SupportModal,
  },

  layout: 'default-no-header',

  data() {
    return {
      formData: {
        customer: {
          contact: '',
          fullName: '',
        },
        qrCode: {
          qrcodeId: null,
        }
      },
      listTable: [],
      isValidate: false,
    };
  },

  head() {
    return {
      title: 'Client',
    };
  },

  computed: {
    ...mapState('clientView', {
      orderId: (state) => state.orderId,
    }),
  },

  mounted() {
    const path = window.location.toString();
    const qrCodeId = path.split('table/');
    this.formData.qrCode.qrcodeId = parseInt(qrCodeId[1]);
    this.$router.push({
      name: 'specific_table',
      params: {
        qrcodeId: this.formData.qrCode.qrcodeId,
      }
    });
    this.connect();
  },

  methods: {
    ...mapMutations('clientView', {
      updateOrderId: 'updateOrderId'
    }),

    connect() {
      this.socket = new SockJs('https://project-for-fos-mld.herokuapp.com/ws');
      this.stompClient = StompClient.over(this.socket);
      this.stompClient.connect({}, this.onConnected);
    },

    onConnected() {
      this.stompClient.subscribe('/topic/staffRoom', this.onMessageReceived);
    },

    sendMessage(id) {
      if (this.stompClient) {
        const tables = {
          tableId: id,
          statusTable: false,
        };
        this.stompClient.send('/app/chat.sendMessage', JSON.stringify(tables), {});
      }
    },

    onMessageReceived(payload) {
      // const message = JSON.parse(payload.body);

      // if(message.type === 'CHAT') {
      //     const element = document.getElementById(message.content);
      //     element.style.backgroundColor = '#00FF00';
      // }
    },

    getQrCodeId() {
      const path = window.location.toString();
      return parseInt(path.substr(path.length - 1));
    },

    validateBeforeOrder() {
      if (!this.validator) {
        return '/';
      }

      return `/khach-hang/order/${this.formData.qrCode.qrcodeId}`;
    },

    async postOrder() {
      const requestParam = {
        customer: {
          contact: this.formData.customer.contact,
          fullName: this.formData.customer.fullName,
        },
        qrCode: {
          qrcodeId: this.formData.qrCode.qrcodeId,
        },
      };

      const res = await orderService.postOrder(requestParam);

      if (res.success) {
        this.sendMessage(this.formData.qrCode.qrcodeId);
        this.updateOrderId(res.data.orderId);
        this.isValidate = true;
        localStorage.setItem('tableId', this.formData.qrCode.qrcodeId);
        localStorage.setItem('orderId', res.data.orderId);
        localStorage.setItem('createAt', res.data.submitTime);
        localStorage.setItem('customerName', res.data.customer.fullName);
        this.$router.push(`/khach-hang/order/${res.data.orderId}`);
        // this.getOrder
      }
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
      if (!this.formData.customer.fullName) {
        Vue.$toast.error('Bạn chưa nhập Họ và tên');
        document.getElementById('fullname').focus();
        return false;
      }

      if (!regexName.test(this.removeAscent(this.formData.customer.fullName))) {
        Vue.$toast.error('Họ và tên phải là chữ cái và có đội dài từ 2 kí tự');
        this.formData.customer.fullName = '';
        document.getElementById('fullname').focus();
        return false;
      }

      if (!this.formData.customer.contact) {
        Vue.$toast.error('Bạn chưa nhập Số điện thoại');
        document.getElementById('phone').focus();
        return false;
      }

      const regexPhone = /(84|0[3|5|7|8|9])+([0-9]{8})\b/g;
      if (!regexPhone.test(this.formData.customer.contact)) {
        Vue.$toast.error('Bạn chưa nhập đúng định dạng số điện thoại');
        this.formData.customer.contact = '';
        document.getElementById('phone').focus();
        return false;
      }

      return true;
    },

    handleGetOrder() {
      if (!this.validator()) {
        this.isValidate = false;
        this.$router.push(`/khach-hang/table/${this.formData.qrCode.qrcodeId}`);
        return;
      }
      this.isValidate = true;

      if (isEmpty(this.formData.customer.contact)) {
        return;
      }
      this.postOrder();
    },

    handleShowSupportModal() {
      this.$refs.supportModalRef.show();
    }
  }
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
