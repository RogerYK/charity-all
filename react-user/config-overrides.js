const {override, fixBabelImports, addDecoratorsLegacy, useEslintRc, addLessLoader} = require('customize-cra');

module.exports = override(
  fixBabelImports('import', {
    libraryName: 'antd',
    libraryDirectory: 'es',
    style: true
  }),
  addLessLoader({
    javascriptEnabled: true,
    modifyVars: {
      '@primary-color': '#00cba6',
      '@link-color': '#425eb2',
      '@text-color': '#222c37',
      '@text-color-second': '#7a8087',
      '@border-radius-base': '0',
      '@border-color-base': '#dee0e1',
    },
  }),
  addDecoratorsLegacy(),
  useEslintRc()
);