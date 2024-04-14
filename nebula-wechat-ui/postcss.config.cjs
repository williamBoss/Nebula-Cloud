module.exports = {
    plugins: {
        'autoprefixer': {}, // 自动添加浏览器前缀
        'postcss-preset-env': { // 使用未来的 CSS 特性
            stage: 1,
            features: {
                'nesting-rules': true
            }
        }
    }
}
