<template>
  <b-modal
    ref="detailOrder"
    centered
    title="Món ăn"
    body-class="modal__body"
    content-class="modal__content"
    scrollable
    hide-footer
    @hidden="handleHideModal"
  >
    <div class="detail">
      <div class="detail__head">
        <div class="detail__col">Hình ảnh</div>
        <div class="detail__col">Thời gian đặt</div>
        <div class="detail__col">Số lượng</div>
        <div class="detail__col">Giá</div>
      </div>

      <div class="detail__list">
        <template v-if="isLoad">
          <div class="loading">
            <Loading />
          </div>
        </template>

        <template v-else>
          <div v-for="(item, i) in listDetail" :key="'detail' + i" class="detail__item">
            <div class="detail__row detail__img">
              <img :src="item.dishes.dishImage" alt="" />
            </div>
            <div class="detail__row">{{ convertTime(item.createDate) }}</div>
            <div class="detail__row">{{ item.quantity }}</div>
            <div class="detail__row">{{ item.price }} đ</div>
          </div>
        </template>
      </div>
    </div>
  </b-modal>
</template>

<script>
import moment from 'moment';
import { orderService } from '@/services';
import Loading from '@/components/common/Loading/index.vue';

export default {
  name: 'AddUserModal',

  components: {
    Loading,
  },

  data() {
    return {
      listDetail: [],
      isLoad: false,
    };
  },

  methods: {
    show(id) {
      console.log(id);
      this.$refs.detailOrder.show();
      this.getDetail(id);
    },

    handleHideModal() {
      this.listDetail = [];
      this.$refs.detailOrder.hide();
    },

    async getDetail(id) {
      this.isLoad = true;
      const res = await orderService.getOrderItem(id).finally(() => {
        this.isLoad = false;
      });

      if (res.success) {
        this.listDetail = res.data;
      }
    },

    convertTime(time) {
      if (time) {
        return moment(time).format('HH:mm DD/MM/YYYY');
      }
      return '';
    },
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
