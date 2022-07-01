<script>
import DefaultAvatar from '../DefaultAvatar/index.vue';

export default {
  name: 'ImageOrDefault',

  components: {
    DefaultAvatar,
  },

  props: {
    src: {
      type: String,
      default: null,
    },

    alt: {
      type: String,
      default: null,
    },
  },

  data() {
    return {
      avatarLoadedError: false,
      styles: {
        visibility: 'hidden',
      },
    };
  },

  methods: {
    handleLoadAvatarError() {
      this.avatarLoadedError = true;
    },

    handleLoadAvatarLoaded() {
      this.styles.visibility = 'visible';
    },
  },

  render() {
    if (this.src && !this.avatarLoadedError) {
      return (
        <img
          src={this.src}
          onError={this.handleLoadAvatarError}
          onLoad={this.handleLoadAvatarLoaded}
          style={this.styles}
        />
      );
    }

    if (this.$slots.default) {
      return this.$slots.default;
    }

    return <DefaultAvatar name={this.alt} />;
  },
};
</script>

<style scoped lang="scss" src="./styles.scss"></style>
