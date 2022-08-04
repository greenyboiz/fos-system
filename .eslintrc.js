module.exports = {
  root: true,
  env: {
    browser: true,
    node: true,
  },
  parserOptions: {
    ecmaVersion: 12,
    parser: '@babel/eslint-parser',
    requireConfigFile: false,
    sourceType: 'module',
    babelOptions: {
      parserOpts: {
        plugins: ['jsx']
      }
    }
  },
  extends: ['@nuxtjs', 'plugin:nuxt/recommended', 'prettier'],
  plugins: [],
  // add your custom rules here
  rules: {
    'vue/multi-word-component-names': 'off',
    'lodash/prefer-lodash-method': 'off',
    'lodash/import-scope': 'off',
    'lodash/prefer-noop': 'off',

    'arrow-parens': 'off',
    'import/order': 'off',
    'comma-dangle': 'off',
    'no-console': 'off',
    'no-debugger': 'warn',
    'no-var': 'error',
    'no-unused-vars': 'off',
    'block-scoped-var': 'error',
    'no-param-reassign': 'off',
    semi: ['warn', 'always'],
    'space-before-function-paren': ['off', 'never'],
    'key-spacing': [
      'warn',
      {
        mode: 'minimum',
        beforeColon: false,
        afterColon: true,
      },
    ],
    'arrow-spacing': ['warn'],
    'spaced-comment': ['warn', 'always'],
    'space-in-parens': ['warn', 'never'],
    'object-curly-spacing': ['warn', 'always'],
    'no-trailing-spaces': 'warn',
    'object-shorthand': ['warn', 'methods'],
    'prefer-template': 'warn',
    'no-useless-concat': 'warn',
    'prefer-const': 'error',
    yoda: ['warn', 'never', { exceptRange: true }],
    // "indent": ["warn", 2, { "SwitchCase": 1 }],
    quotes: ['warn', 'single', { avoidEscape: true }],
    'max-len': [
      'warn',
      120,
      {
        ignoreComments: true,
        ignoreUrls: true,
        ignoreStrings: true,
        ignoreTemplateLiterals: true,
        ignorePattern: 'd="([\\s\\S]*?)"|class="([\\s\\S]*?)"'
      },
    ],
    'no-constant-condition': [
      'warn',
      {
        checkLoops: false,
      },
    ],
    'brace-style': ['warn'],
    eqeqeq: ['warn', 'always'],
    camelcase: 'off',

    // vue
    'vue/attribute-hyphenation': 'off',
    'vue/html-self-closing': 'off',
    'vue/singleline-html-element-content-newline': 'off',
    'vue/max-attributes-per-line': 'off',
    'vue/html-indent': 'off',
    'vue/html-closing-bracket-newline': 'off',
  },
};
