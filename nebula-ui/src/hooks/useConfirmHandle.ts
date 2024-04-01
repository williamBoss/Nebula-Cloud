import { ElMessage as elMessage, ElMessageBox as elMessageBox } from 'element-plus'

/**
 * @description 操作单条数据信息(二次确认【删除、禁用、启用、重置密码】)
 * @param {Function} api 操作数据接口的api方法(必传)
 * @param {Object} params 携带的操作数据参数 {id,params}(必传)
 * @param {String} message 提示信息(必传)
 * @param {String} confirmType icon类型(不必传,默认为 warning)
 * @return {Promise}
 */
export const useConfirmHandle = (
  api: (params: any) => Promise<any>,
  params: any,
  message: string,
  confirmType: string = 'warning'
): Promise<boolean> => {
  return new Promise((resolve, reject) => {
    elMessageBox
      .confirm(`是否${message}?`, '温馨提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: confirmType as any,
        draggable: true
      })
      .then(async () => {
        const res = await api(params)
        if (!res) return reject(new Error('失败'))
        elMessage({
          type: 'success',
          message: `${message}成功!`
        })
        resolve(true)
      })
      .catch(() => {
        // 用户取消操作
        resolve(false)
      })
  })
}
