const {override, fixBabelImports, addDecoratorsLegacy, useEslintRc} = require('customize-cra');

module.exports = override(
  fixBabelImports('import', {
    libraryName: 'antd',
    libraryDirectory: 'es',
    style: 'css'
  }),
  addDecoratorsLegacy(),
  useEslintRc()
);