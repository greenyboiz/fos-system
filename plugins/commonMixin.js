import { isNumber, isNaN, size } from 'lodash';

export default {
  methods: {
    currencyFormatter (num) {
      if (!num) return 0;
      if (Number.parseInt(num)<1) return 0;
      if (isNumber(num)) {
        return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
      } else {
        const parsedNumber = this.currencyParser(num);
        if (isNaN(parsedNumber)) {
          return 0;
        } else {
          let result = parsedNumber.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
          if(size(result) > 1 && result.charAt(0) === '0') {
            result = result.substring(1);
          }
          return result;
        }
      }
    },
  }
};
