import { ElMessageBox as elMessageBox } from 'element-plus'

/**
 * @description 操作单条数据信息(二次确认【删除、禁用、启用】)
 * @param {String} message 提示信息(必传)
 * @param {String} confirmType icon 类型(不必传,默认为 warning)
 * @return {Promise<string | null>}
 */
export const usePromptHandle = (message: string, confirmType: string = 'warning'): Promise<string | null> => {
  return elMessageBox.prompt(`${message}?`, '温馨提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: confirmType as any,
    draggable: true
  })
}
