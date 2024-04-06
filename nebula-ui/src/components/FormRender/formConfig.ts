import { cloneDeep } from 'lodash'

interface DefaultFormConfig {
  modelName: string
  rulesName: string
  size: string
  labelPosition: string
  labelAlign: string
  customClass: any[]
  labelWidth: number
  refName: string
}

interface DefaultFormJson {
  widgetList: any[]
  formConfig: DefaultFormConfig | undefined
}

/**
 * 默认表单配置
 */
export function getDefaultFormConfig(): DefaultFormConfig {
  return {
    modelName: 'formData',
    refName: 'formRef',
    rulesName: 'rules',
    labelWidth: 80,
    labelPosition: 'left',
    size: '',
    labelAlign: 'label-left-align',
    customClass: []
  }
}

/**
 * 默认表单配置
 */
export function buildDefaultFormJson(): DefaultFormJson {
  return {
    widgetList: [],
    formConfig: cloneDeep(getDefaultFormConfig())
  }
}
