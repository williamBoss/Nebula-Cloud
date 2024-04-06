import { computed, onMounted, shallowRef } from 'vue'
import { loadComponents } from '@components/loadComponents.js'

export interface WidgetCommonProps {
  widget: any
}

export const widgetCommonDefaultProps: WidgetCommonProps = {
  widget: Object
}

export const useCommonComputed = (props: any) => {
  const components = shallowRef({})
  const customClass = computed(() => (props.widget && props.widget.options.customClass) || '')

  onMounted(async () => {
    components.value = await loadComponents()
  })

  const getComponentByContainer = (comp: any) => {
    return components.value[comp.type + '-container']
  }

  const getSelfComponent = (widget: any) => {
    return components.value[widget.type]
  }

  return {
    customClass,
    components,
    getComponentByContainer,
    getSelfComponent
  }
}
