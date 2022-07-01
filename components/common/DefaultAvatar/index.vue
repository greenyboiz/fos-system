<template>
  <div class="defaultAvatar">
    <h4>{{ getFirstLetter() }}</h4>
  </div>
</template>

<script>
export default {
  name: 'DefaultAvatar',

  props: {
    name: {
      type: String,
      default: ''
    }
  },

  methods: {
    getFirstLetter() {
      if (this.name) {
        let nameNotation = '';
        const str = this.name.trim().split(' ');
        nameNotation = nameNotation + str[0].charAt(0) + str[str.length - 1].charAt(0);
        nameNotation = nameNotation.toUpperCase();
        return nameNotation;
      } else {
        return 'NF';
      }
    },
  },
};
</script>

<style scoped lang="scss" src="./styles.scss"></style>
