<template>
  <svg
    :width="width"
    :height="height"
    viewBox="0 0 20 20"
    fill="none"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      d="M3.63822 10.8563C3.30226 10.5182 3.31202 9.98002 3.66001 9.6543C4.00801 9.32857 4.56247 9.33862 4.89844 9.67675L8.62468 13.427L8.09455 13.9232C7.69133 14.3006 7.0489 14.289 6.65963 13.8972L3.63822 10.8563Z"
      :fill="currentColor"
    />
    <path
      d="M14.7647 5.46399C15.128 5.1245 15.7064 5.13544 16.0565 5.48842C16.4067 5.8414 16.3961 6.40275 16.0328 6.74224L8.21385 14.0494C7.81034 14.4265 7.16792 14.4143 6.77896 14.0223L6.21515 13.4539L14.7647 5.46399Z"
      :fill="currentColor"
    />
  </svg>
</template>
<script>
export default {
  name: 'CheckIcon',
  props: {
    currentColor: {
      type: String,
      default: '#069255'
    },
    width: {
      type: Number,
      default: 20
    },
    height: {
      type: Number,
      default: 20
    }
  }
};
</script>
