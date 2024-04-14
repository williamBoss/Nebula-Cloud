import { Request } from '@/request/request'

export class WechatService {
  /**
   * 微信请求方法
   */
  static wechat = {
    /**
     * 微信授权
     * config:
     *  appId - 公众号appId
     *  REDIRECT_URI - 回调域名，授权后跳转的地址
     *  SCOPE - 授权类型，snsapi_userinfo／snsapi_base
     */
    auth: (REDIRECT_URI: string) => {
      const appid = process.env.VITE_APP_WECHAT_APPID,
        SCOPE = 'snsapi_userinfo'
      window.location.href =
        'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' +
        appid +
        '&redirect_uri=' +
        encodeURIComponent(REDIRECT_URI) +
        '&response_type=code&scope=' +
        SCOPE +
        '&state=1#wechat_redirect'
    },

    /**
     * 获取授权后的code
     */
    getCode: () => {
      // 如果连接中有微信返回的 code，则用此 code 调用后端接口，向微信服务器请求用户信息
      const searchParams = new URLSearchParams(window.location.search)
      return searchParams.get('code')
    },

    /**
     * 获取微信用户信息
     * @param code
     */
    wechatUserInfo: (code: string) => Request.get('/wechat/config/user-info', { code })
  }
}
