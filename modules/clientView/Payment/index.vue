<template>
  <div class="payment">
    <div class="payment__header">
      Phương thức thanh toán
    </div>
    <div class="payment__method">
      <div v-for="item in payment" :key="item.key" class="method-item">
        <div class="method-title">
          <img v-if="item.key === 'banking_method'" src="@/assets/icons/banking-icon.png" alt="">
          <img v-if="item.key === 'cash_method'" src="@/assets/icons/cash-icon.png" alt="">
          <img v-if="item.key === 'scanQr_method'" src="@/assets/icons/scan-icon.png" alt="">
        </div>
        <div class="method-select">
          <CustomCheckbox
            v-model="selectedMethod"
            :inputValue="item.key"
            :keyValue="item.key"
            :customLabel="true"
            styleCheck="dot"
            shape="circle"
            type="radio"
          >
            <template slot="custom-label">
              <span>{{ item.name }}</span>
            </template>
          </CustomCheckbox>
        </div>
      </div>
    </div>
    <!-- <div class="totalPrice">
      <div class="info-item">
        <div class="info-item_title">Giá:</div>
        <div class="info-item_number">2.500.000 VNĐ</div>
      </div>
      <div class="info-item">
        <div class="info-item_title">VAT:</div>
        <div class="info-item_number">10%</div>
      </div>
      <div class="info-total">
        <div class="info-item_title">Tổng cộng:</div>
        <div class="info-item_number">2.750.000 VNĐ</div>
      </div>
    </div> -->
    <div class="payment__info">
      <div v-if="selectedMethod === 'banking_method'" class="banking-info">
        <div>Ngân hàng: ViB</div>
        <div>Số tài khoản: 010595284</div>
        <div>Chủ tài khoản: Nguyễn Đức Mạnh</div>
        <div>Nội dung: abcxyz</div>
      </div>
      <div v-if="selectedMethod === 'cash_method'" class="cash-info">
        <button>Gọi nhân viên thanh toán</button>
      </div>
      <div v-if="selectedMethod === 'scanQr_method'" class="scanQr-info">
        <img src="@/assets/images/cocktail.png" alt="">
      </div>
    </div>
  </div>
</template>

<script>
import CustomCheckbox from '@/components/common/CustomCheckbox/index.vue';
export default {
  name: 'Payment',

  components: {
    CustomCheckbox,
  },

  layout: 'default-no-header',

  data() {
    return {
      payment: [
        { id: 1, name: 'Chuyển khoản', key: 'banking_method' },
        { id: 2, name: 'Tiền mặt', key: 'cash_method' },
        { id: 3, name: 'QR', key: 'scanQr_method' },
      ],
      selectedMethod: '',
    };
  },

  head() {
    return {
      title: 'Payment',
    };
  },
};
</script>

<style lang="scss" src="./styles.scss" scoped></style>
