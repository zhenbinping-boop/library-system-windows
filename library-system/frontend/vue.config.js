const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true
      }
    }
  },
  configureWebpack(config) {
    const htmlPlugins = config.plugins.filter(
      p => p.constructor.name === 'HtmlWebpackPlugin'
    )
    if (htmlPlugins.length > 1) {
      for (let i = 1; i < htmlPlugins.length; i++) {
        const idx = config.plugins.indexOf(htmlPlugins[i])
        config.plugins.splice(idx, 1)
      }
    }
  }
})
