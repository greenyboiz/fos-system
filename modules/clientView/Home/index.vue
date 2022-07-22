<template>
  <div class="client-home">
    <div class="headline">
      <div class="headline__title">MLD Restaurent</div>
      <div class="headline__support" @click="handleShowSupportModal()">Support</div>
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
        <label for="username">Họ và tên:</label>
        <input id="username" type="text" placeholder="Nhập họ và tên" />
      </div>
      <div class="customer__item">
        <label for="phone">SĐT:</label>
        <input id="phone" v-model="formData.customer.contact" type="text" placeholder="Nhập SĐT" required />
      </div>
    </div>
    <div class="getDish" @click="handleGetOrder()">
      <nuxt-link to="/khach-hang/order">
        <button>Gọi món</button>
      </nuxt-link>
    </div>
    <SupportModal ref="supportModalRef" />
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
import VueSlickCarousel from 'vue-slick-carousel';
import { orderService, tableManagementService } from '@/services';
import { isEmpty } from 'lodash';
import SupportModal from '@/components/common/SupportModal/index.vue';
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
        },
        qrCode: {
          qrcodeId: 1,
        }
      },
      listTable: [],
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

  created() {
    this.$router.push({
      params: {
        qrcodeId: this.formData.qrCode.qrcodeId
      }
    });
  },

  methods: {
    ...mapMutations('clientView', {
      updateOrderId: 'updateOrderId'
    }),

    async postOrder() {
      const requestParam = {
        customer: {
            contact: this.formData.customer.contact
        },
        qrCode: {
            qrcodeId: this.formData.qrCode.qrcodeId
        },
      };

      const res = await orderService.postOrder(requestParam);

      if (res.success) {
        this.updateOrderId(res.data.orderId);
        // this.$router.push({
        //   name: 'specific_order',
        //   params: {
        //     orderId: res.data.orderId,
        //   },
        // });
      }
    },

    handleGetOrder() {
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
