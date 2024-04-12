import { ConfigEnv, defineConfig, loadEnv, UserConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import { VantResolver } from '@vant/auto-import-resolver'
import components from 'unplugin-vue-components/vite'
import VitePluginVueDevTools from 'vite-plugin-vue-devtools'
import unpluginSvgComponent from 'unplugin-svg-component/vite'
import icons from 'unplugin-icons/vite'
import { createHtmlPlugin } from 'vite-plugin-html'

// https://vitejs.dev/config/
export default defineConfig(({ mode }: ConfigEnv): UserConfig => {
  const env = loadEnv(mode, process.cwd())
  return {
    // 项目部署在主域名的子文件使用,例如http://localhost:3000/myvite/。不填默认就是/
    base: env.VITE_BASE_PATH || '/',
    resolve: {
      alias: {
        '@': resolve(__dirname, 'src'),
        '@api': resolve(__dirname, 'src/api'),
        '@components': resolve(__dirname, 'src/components')
      },
      extensions: ['.js', '.json', '.ts', '.mjs'] // 使用路径别名时想要省略的后缀名，可以自己 增减
    },
    define: {
      'process.env': { ...env } // 将加载的环境变量注入到 Vite 的构建过程中
    },
    plugins: [
      vue(),
      VitePluginVueDevTools(),
      icons({
        autoInstall: true,
        compiler: 'vue3'
      }),
      components({
        resolvers: [VantResolver()]
      }),
      unpluginSvgComponent({
        iconDir: [resolve(__dirname, 'src/assets/icons/svg')], // 图标文件夹位置
        projectType: 'vue',
        dts: true,
        dtsDir: resolve(__dirname, 'src/assets/icons/svg'),
        prefix: 'icon',
        componentName: 'SvgIcon',
        symbolIdFormatter(name, prefix) {
          const nameArr = name.split('/')
          if (prefix) {
            nameArr.unshift(prefix)
          }
          return nameArr.join('-').replace(/\.svg$/, '')
        },
        svgSpriteDomId: 'id-svg',
        vueVersion: 3,
        treeShaking: false
      }),
      createHtmlPlugin({
        inject: {
          data: {
            title: env.VITE_APP_TITLE // 将环境变量 VITE_APP_TITLE 赋值给 title 方便 html页面使用 title 获取系统标题
          }
        }
      })
    ],
    // 自定义端口号，默认为3000
    server: {
      host: '127.0.0.1',
      port: Number(env.VITE_PORT),
      // 在开发服务器启动时自动在浏览器中打开应用程序
      open: env.VITE_OPEN,
      // 代理配置
      proxy: {
        '^/dev-api/.*': {
          target: env.VITE_PROXY_URL,
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/dev-api/, '')
        }
      }
    },
    // 打包配置
    build: {
      // 文件夹名称
      outDir: env.VITE_OUT_DIR || 'dist',
      assetsDir: 'assets',
      // 去掉console语句
      terserOptions: {
        compress: {
          drop_console: true
        }
      }
    }
  }
})
