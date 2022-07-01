// import Vue from 'vue';
// import * as rules from 'vee-validate/dist/rules';
// import {
//   ValidationObserver,
//   ValidationProvider,
//   configure,
//   extend
// } from 'vee-validate';
// import Vuelidate from 'vuelidate';
// import { helpers } from 'vuelidate/lib/validators';
// import { trim, upperFirst } from 'lodash';

// const veeValidateConfig = {
//   bails: true,
//   skipOptional: true,
//   mode: 'aggressive',
//   useConstraintAttrs: true,
//   defaultMessage: () => '{_field_} thiếu'
// };

// // Sets the options.
// configure(veeValidateConfig);

// // Install VeeValidate rules and localization
// Object.keys(rules).forEach((rule) => {
//   // eslint-disable-next-line import/namespace
//   extend(rule, rules[rule]);
// });
// extend('numeric', {
//   ...rules.numeric,
//   message: (fieldName, values) => {
//     return 'Nhập số';
//   }
// });
// extend('required', {
//   ...rules.required,
//   message: (fieldName, values) => {
//     let append = 'thiếu';
//     if (!trim(fieldName)) {
//       append = upperFirst(append);
//     }
//     return `${fieldName} ${append}`;
//   }
// });

// extend('min', {
//   ...rules.min
// });

// extend('max', {
//   ...rules.max
// });

// extend('mobile', {
//   validate: (value) => {
//     return /(03|05|07|08|09)+([0-9]{8})\b/.test(value);
//   },
//   message: (_, values) => 'Sai định dạng'
// });
// const phone = helpers.regex(
//   'phone',
//   /^((08|09|03|07|05|04|02[0123456789]|06)|(([+]{0,1}84)(1[2689]|8|9|3|7|5|4|2[0123456789]|6)))[0-9]{8}$/
// );

// const passwordValidate = helpers.regex(
//   'password',
//   // eslint-disable-next-line max-len
//   /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~])[A-Za-z\d!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~]{8,16}$/
// );
// const moneyRange = (param) => (value) =>
//   !helpers.req(value) ||
//   value === param.minValue ||
//   (value >= param.startValue && value <= param.endValue);
// const moneyValidate = (value) => (Number.parseInt(value) && Number.parseInt(value) > 0) || value?.length > 3;

// export const customValidator = {
//   phone,
//   moneyRange,
//   moneyValidate,
//   passwordValidate
// };
// Vue.use(Vuelidate);
// // Install VeeValidate components globally
// Vue.component('ValidationObserver', ValidationObserver);
// Vue.component('ValidationProvider', ValidationProvider);
