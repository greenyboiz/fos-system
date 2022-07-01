<template>
  <div class="checkbox-custom" :class="{ indeterminate: indeterminate, disabled: disabled }">
    <input
      :id="keyValue"
      v-model="dataSelected"
      :type="type"
      :value="inputValue"
      class="form-check-input"
      :disabled="disabled"
      @change="$emit('onChange')"
    />
    <label class="form-check-label" :class="{ circle: shape === 'circle', dot: styleCheck === 'dot' }" :for="keyValue">
      <slot v-if="customLabel" name="custom-label"></slot>
      <template v-else>
        {{ label }}
      </template>
    </label>
  </div>
</template>

<script>
export default {
  name: 'CustomCheckbox',

  props: {
    type: {
      type: String,
      required: false,
      default: 'checkbox',
    },
    styleCheck: {
      type: String,
      required: false,
      default: '',
    },
    label: {
      type: String,
      required: false,
      default: () => '',
    },
    inputValue: {
      type: [String, Object, Boolean, Number, Array],
      required: false,
      default: () => '',
    },
    value: {
      default: () => '',
      type: [String, Object, Boolean, Number, Array]
    },
    shape: {
      type: String,
      required: false,
      default: () => 'square',
    },
    keyValue: {
      type: [String, Number],
      required: false,
      default: () => '',
    },
    customLabel: {
      type: Boolean,
      required: false,
      default: false,
    },
    disabled: {
      type: Boolean,
      required: false,
      default: false,
    },
    indeterminate: {
      type: Boolean,
      required: false,
      default: false,
    },
  },
  computed: {
    dataSelected: {
      get() {
        return this.value;
      },
      set(value) {
        this.$emit('input', value);
      },
    },
  },

  methods: {},
};
</script>
<style scoped lang="scss" src="./index.scss"></style>
