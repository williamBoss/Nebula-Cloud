export default defineAppConfig({
  pages: [
    'pages/index/index',
      'pages/login/index',
  ],
  window: {
    backgroundTextStyle: 'light',
    navigationBarBackgroundColor: '#fff',
    navigationBarTitleText: 'WeChat',
    navigationBarTextStyle: 'black'
  },
  subpackages: [],
  tabBar:{
    color: '#000',
    selectedColor: '#000',
    backgroundColor: '#000',
    borderStyle: 'white',
    list:[
      {
        pagePath: 'pages/index/index',
        text: '首页'
      },
      {
        pagePath: 'pages/login/index',
        text:'我的'
      }
    ]
  },
  subPackages:[]
})
