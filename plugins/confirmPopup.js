class ConfirmPopup {

  open(options) {
    window.$nuxt.$root.$emit('openConfirmPopup', options);
  }

  close() {
    window.$nuxt.$root.$emit('closeConfirmPopup');
  }
}

// eslint-disable-next-line no-empty-pattern
const plugin = ({}, inject) => {
  const confirmPopup = new ConfirmPopup();
  inject('confirmPopup', confirmPopup);
};

export default plugin;
