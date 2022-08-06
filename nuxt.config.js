export default {
  ssr: false,
  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    title: 'food-ordering-system',
    htmlAttrs: {
      lang: 'en',
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' },
      { name: 'format-detection', content: 'telephone=no' },
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' },
      {
        rel: 'stylesheet',
        href: 'https://fonts.googleapis.com/css?family=Roboto&display=swap'
      },
      {
        rel: 'stylesheet',
        href: 'https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css'
      }
    ],
    script: [
      {
        src: 'https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js',
      },
      {
        src: 'https://unpkg.com/vue-multiselect@2.1.0',
      },
      {
        src: 'https://upload-widget.cloudinary.com/global/all.js',
      },
      {
        src: 'https://unpkg.com/vuejs-paginate@latest'
      }
    ],
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css: [
    // Load a Node.js module directly (here it's a Sass file)
    // CSS file in the project
    // '~/assets/css/main.css',
    // SCSS file in the project
    // '~/assets/css/main.scss'
    '@/assets/css/main.scss',
    'vue-slick-carousel/dist/vue-slick-carousel.css',
    'vue-slick-carousel/dist/vue-slick-carousel-theme.css'
  ],

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [
    '@/plugins/validators.js',
    '@/plugins/confirmPopup.js'
  ],

  // Auto import components: https://go.nuxtjs.dev/config-components
  components: true,

  // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
  buildModules: [
    // https://go.nuxtjs.dev/eslint
    '@nuxtjs/eslint-module',
    '@nuxt/postcss8',
    '@nuxtjs/router-extras'
  ],

  // Modules: https://go.nuxtjs.dev/config-modules
  modules: [
    // https://go.nuxtjs.dev/bootstrap
    'bootstrap-vue/nuxt',
    // https://go.nuxtjs.dev/axios
    '@nuxtjs/axios',
    '@nuxtjs/toast',
    '@nuxtjs/auth',
  ],

  router: {
    middleware: ['auth']
  },

  toast: {
    theme: 'bubble',
    position: 'bottom-center',
    duration: 4000
  },

  // Axios module configuration: https://go.nuxtjs.dev/config-axios
  axios: {
    baseURL: 'https://project-for-fos-mld.herokuapp.com/api/',
    // debug: process.env.DEBUG || false,
    // proxyHeaders: false,
    // credentials: false,
  },

  auth: {
    strategies: {
      local: {
        endpoints: {
          login: { url: 'login', method: 'post', propertyName: 'data.token' },
          user: { url: 'userLogin', method: 'get', propertyName: 'data' },
          logout: false
        },
        token: {
          property: 'access_token',
          global: true,
          type: 'Bearer'
        }
      }
    },
    redirect: {
      login: '/',
      logout: '/',
      callback: '/',
      home: '/'
    },
  },

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {
    postcss: {
      plugins: {
        tailwindcss: {},
        autoprefixer: {},
      },
    },
  },
};
