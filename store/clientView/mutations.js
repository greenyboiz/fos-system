export default {
  updateDishId(state, data) {
    state.dishId = data;
  },

  updateOrderId(state, data) {
    state.orderId = data;
  },

  updateTotalPriceOrder(state, data) {
    state.totalPriceOrder = data;
  },

  updateOrderItemList(state, data) {
    state.orderItemList = data;
  }
};
