import axios from './axios.js'
import { ApiResponse } from '@api/types/sys-api'

/**
 * 请求方法
 */
export class Request {
  /**
   * get方法
   * @param url 路径
   * @param params 参数
   * @return 返回
   */
  static get = <T>(url: string, params = {}): Promise<T> => {
    return new Promise((resolve, reject) => {
      axios
        .get(url, { params })
        .then((res) => {
          const { data }: { data: ApiResponse<T> } = res
          resolve(data.data)
        })
        .catch((err) => {
          reject(err)
        })
    })
  }

  /**
   * post方法
   * @param url 路径
   * @param params 参数
   * @param config 请求头配置
   * @return 返回
   */
  static post = <T>(url: string, params = {}, config = {}): Promise<ApiResponse<T>> => {
    return new Promise((resolve, reject) => {
      axios
        .post(url, params, config)
        .then((res) => {
          resolve(res.data)
        })
        .catch((err) => {
          reject(err)
        })
    })
  }

  /**
   * put方法
   * @param url 路径
   * @param params 参数
   * @return 返回
   */
  static put = <T>(url: string, params = {}): Promise<ApiResponse<T>> => {
    return new Promise((resolve, reject) => {
      axios
        .put(url, params)
        .then((res) => {
          resolve(res.data)
        })
        .catch((err) => {
          reject(err)
        })
    })
  }

  /**
   * delete方法
   * @param url 路径
   * @param params 参数
   * @return 返回
   */
  static delete = <T>(url: string, params = {}): Promise<ApiResponse<T>> => {
    return new Promise((resolve, reject) => {
      axios
        .delete(url, params)
        .then((res) => {
          resolve(res.data)
        })
        .catch((err) => {
          reject(err)
        })
    })
  }
}
