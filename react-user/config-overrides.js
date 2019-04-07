const rewireSass = require('react-app-rewire-sass-modules')
const rewireLess = require('react-app-rewire-less')
const {injectBabelPlugin}  = require('react-app-rewired')
const rewireCssModules = require('react-app-rewire-css-modules')
const path = require('path')

module.exports = function override(config, env) {
  // do stuff with the webpack config...
  config = injectBabelPlugin(
       ['import', { libraryName: 'antd', libraryDirectory: 'es', style: true }],
       config,
     );
  config = rewireLess.withLoaderOptions({
      modifyVars: {"@primary-color": "#589f23"},
      javascriptEnabled: true,
  })(config, env);
  config = rewireCssModules(config, env);
  config.resolve.alias['@'] = path.resolve('src')
  return config;
};