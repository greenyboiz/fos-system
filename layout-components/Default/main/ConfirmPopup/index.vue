<template>
  <b-modal
    id="confirmUniqueId"
    ref="modalRef"
    body-class="comfirmBox__body"
    content-class="comfirmBox"
    centered
    hide-footer
    hide-header
    :no-close-on-backdrop="config.noCloseOnBackdrop"
  >
    <div>
      <div v-if="!config.hideTitle" class="tw-font-medium tw-text-center tw-pt-3 tw-text-lg">
        {{ config.title }}
      </div>
      <div class="tw-px-6 tw-text-center tw-mb-4 tw-mt-4 tw-text-sm" :style="config.messageStyle">
        <template v-if="config.rawMessage">
          <!-- eslint-disable-next-line vue/no-v-html -->
          <div v-html="config.message"></div>
        </template>
        <template v-else>
          {{ config.message }}
        </template>
      </div>

      <div class="tw-flex tw-justify-between tw-px-6 tw-pb-3">
        <div
          class="tw-w-[48%] tw-text-center tw-px-0 tw-py-2 tw-cursor-pointer tw-rounded comfirmBoxAction__btn--confirm"
          :class="{ cancelButtonHidden: isHideCancelButton() }"
          :style="config.confirmTextStyle"
          @click="handConfirm()"
        >
          {{ config.confirmText }}
        </div>
        <div
          v-if="!isHideCancelButton()"
          class="tw-w-[48%] tw-text-center tw-px-0 tw-py-2 tw-cursor-pointer tw-rounded comfirmBoxAction__btn--cancel"
          :style="config.cancelTextStyle"
          @click="handCancel()"
        >
          {{ config.cancelText }}
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import { isEmpty } from 'lodash';

export default {
  name: 'ConfirmModal',

  data() {
    return {
      config: this.getDefaultConfig(),
    };
  },

  created() {
    this.$root.$on('openConfirmPopup', this.openPopup);
    this.$root.$on('closeConfirmPopup', this.hidePopup);
  },

  destroyed() {
    this.$root.$off('openConfirmPopup', this.openPopup);
    this.$root.$off('closeConfirmPopup', this.hidePopup);
  },

  methods: {
    openPopup(option) {
      this.config = this.getDefaultConfig();
      this.setOptionToState(option);
      this.$refs.modalRef.show();
    },

    hidePopup() {
      this.$refs.modalRef.hide();
    },

    setOptionToState(option) {
      if (isEmpty(option)) {
        return;
      }

      Object.keys(option).forEach((key) => {
        this.config[key] = option[key];
      });
    },

    getDefaultConfig() {
      return {
        title: 'Title',
        hideTitle: false,
        message: 'Message',
        rawMessage: false,
        messageStyle: {},
        confirmText: 'Đồng ý',
        confirmTextStyle: {},
        cancelText: 'Đóng',
        cancelTextStyle: {},
        confirmed: () => {},
        canceled: () => {},

        // Modal config
        noCloseOnBackdrop: false,
      };
    },

    optionalOrDefault(option, name) {
      return option[name] || this.config[name];
    },

    isHideCancelButton() {
      return this.config.cancelText === false;
    },

    handCancel() {
      this.$refs.modalRef.hide();
      this.config.canceled();
    },

    handConfirm() {
      this.$refs.modalRef.hide();
      this.config.confirmed();
    },
  },
};
</script>

<style scoped lang="scss" src="./styles.scss"></style>
