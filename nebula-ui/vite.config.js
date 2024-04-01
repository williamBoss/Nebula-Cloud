var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
import { defineConfig, loadEnv } from 'vite';
import vue from '@vitejs/plugin-vue';
import vueJsx from '@vitejs/plugin-vue-jsx';
import { resolve } from 'path';
import autoImport from 'unplugin-auto-import/vite';
import components from 'unplugin-vue-components/vite';
import { ElementPlusResolver as elementPlusResolver } from 'unplugin-vue-components/resolvers';
import icons from 'unplugin-icons/vite';
import iconsResolver from 'unplugin-icons/resolver';
import unpluginSvgComponent from 'unplugin-svg-component/vite';
import { createHtmlPlugin } from 'vite-plugin-html';
import elementPlus from 'unplugin-element-plus/vite';
import VitePluginVueDevTools from 'vite-plugin-vue-devtools';
// https://vitejs.dev/config/
export default defineConfig(function (_a) {
    var mode = _a.mode;
    var env = loadEnv(mode, process.cwd());
    return {
        // 项目部署在主域名的子文件使用,例如http://localhost:3000/myvite/。不填默认就是/
        base: env.VITE_BASE_PATH || '/',
        resolve: {
            alias: {
                '@': resolve(__dirname, 'src'),
                '@api': resolve(__dirname, 'src/api'),
                '@components': resolve(__dirname, 'src/components')
            },
            extensions: ['.js', '.json', '.ts'] // 使用路径别名时想要省略的后缀名，可以自己 增减
        },
        define: {
            'process.env': __assign({}, env) // 将加载的环境变量注入到 Vite 的构建过程中
        },
        plugins: [
            vue(),
            vueJsx(),
            VitePluginVueDevTools(),
            elementPlus({}),
            icons({
                autoInstall: true,
                compiler: 'vue3'
            }),
            autoImport({
                imports: ['vue'],
                resolvers: [
                    elementPlusResolver(),
                    iconsResolver() // 自动导入图标组件
                ]
            }),
            components({
                resolvers: [
                    elementPlusResolver(),
                    // 自动注册图标组件
                    iconsResolver({
                        enabledCollections: ['ep']
                    })
                ]
            }),
            unpluginSvgComponent({
                iconDir: [resolve(__dirname, 'src/assets/icons/svg')], // 图标文件夹位置
                projectType: 'vue',
                dts: true,
                dtsDir: resolve(__dirname, 'src/assets/icons/svg'),
                prefix: 'icon',
                componentName: 'SvgIcon',
                symbolIdFormatter: function (name, prefix) {
                    var nameArr = name.split('/');
                    if (prefix) {
                        nameArr.unshift(prefix);
                    }
                    return nameArr.join('-').replace(/\.svg$/, '');
                },
                svgSpriteDomId: 'id-svg',
                vueVersion: 3,
                treeShaking: false
            }),
            createHtmlPlugin({
                viteNext: true,
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
                    rewrite: function (path) { return path.replace(/^\/dev-api/, ''); }
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
    };
});
