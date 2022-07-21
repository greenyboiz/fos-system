export default ({ store, route, redirect }) => {
  // Check if user is connected first
  console.log('dasda');
  if (!store.getters.isAuthenticated) {
    return redirect('/');
  }
};

