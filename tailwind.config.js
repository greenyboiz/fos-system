module.exports = {
  prefix: 'tw-',
  corePlugins: {
    preflight: false,
  },
  content: [
    './components/**/*.{js,vue,ts}',
    './core/**/*.{js,vue,ts}',
    './layouts/**/*.vue',
    './layout-components/**/*.vue',
    './legacy/**/*.vue',
    './modules/**/*.vue',
    './pages/**/*.vue',
    './plugins/**/*.{js,ts}',
  ],
  theme: {
    extend: {
      colors: {
        ghtk: '#00904a',
        moshop: '#069255',
        'gray-ghtk': '#7A7A7A',
      },
    },
  },
  plugins: [],
};
