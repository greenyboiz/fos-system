export default ({ store, route, redirect }) => {
  // Check if user is connected first
  if (!store.getters.isAuthenticated) {
    return redirect('/');
  }
};

