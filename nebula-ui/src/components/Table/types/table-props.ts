import type {
  TableColumnCtx,
  SummaryMethod,
  ColumnStyle,
  ColumnCls,
  CellStyle,
  TreeNode,
  CellCls,
  Sort
} from 'element-plus'
import type { TableOverflowTooltipOptions } from 'element-plus/es/components/table/src/util'
import { Size } from '@components/Table/types/common-props.ts'
import { ColumnExtProp } from '@components/Table/types/column-props.ts'
import { PaginationProps } from '@components/Table/types/pagination-prop.ts'

type Effect = 'dark' | 'light'
type Layout = 'fixed' | 'auto'

type LoadingConfig = {
  /** 显示在加载图标下方的加载文案 */
  text?: string
  /** 自定义加载图标 */
  spinner?: string
  /** 自定义 `svg` 加载图标 (与 `spinner` 相同) */
  svg?: string
  /** 背景遮罩的颜色，默认值：暗黑模式下为`rgba(0, 0, 0, 0.45)`，正常模式下为`rgba(255, 255, 255, 0.45)` */
  background?: string
}

/**
 * @description `element-plus` 的 `table` 属性，未扩展
 * @see {@link https://element-plus.org/zh-CN/component/table.html#table-%E5%B1%9E%E6%80%A7}
 */
type TableProps = {
  /** 显示的数据 */
  data: Array<any>
  /** `Table` 的高度，默认为自动高度。如果 `height` 为 `number` 类型，单位 `px`；如果 `height` 为 `string` 类型，则这个高度会设置为 `Table` 的 `style.height` 的值，`Table` 的高度会受控于外部样式 */
  height?: string | number
  /** `Table` 的最大高度。合法的值为数字或者单位为 `px` 的高度 */
  maxHeight?: string | number
  /** 是否为斑马纹 `table`，默认值为 `false` */
  stripe?: boolean
  /** 是否带有纵向边框，默认值为 `false` */
  border?: boolean
  /** `Table` 的尺寸 */
  size?: Size
  /** 列的宽度是否自撑开，默认值为 `true` */
  fit?: boolean
  /** 是否显示表头，默认值为 `true` */
  showHeader?: boolean
  /** 是否要高亮当前行，默认值为 `false` */
  highlightCurrentRow?: boolean
  /** 当前行的 `key` ，只写属性 */
  currentRowKey?: string | number
  /** 行的 `className` 的回调方法，也可以使用字符串为所有行设置一个固定的 `className` */
  rowClassName?: ColumnCls<any>
  /** 行的 `style` 的回调方法，也可以使用一个固定的 `Object` 为所有行设置一样的 `Style` */
  rowStyle?: ColumnStyle<any>
  /** 单元格的 `className` 的回调方法，也可以使用字符串为所有单元格设置一个固定的 `className` */
  cellClassName?: CellCls<any>
  /** 单元格的 `style` 的回调方法，也可以使用一个固定的 `Object` 为所有单元格设置一样的 `Style` */
  cellStyle?: CellStyle<any>
  /** 表头行的 `className` 的回调方法，也可以使用字符串为所有表头行设置一个固定的 `className` */
  headerRowClassName?: ColumnCls<any>
  /** 表头行的 style 的回调方法，也可以使用一个固定的 Object 为所有表头行设置一样的 Style */
  headerRowStyle?: ColumnStyle<any>
  /** 表头单元格的 `className` 的回调方法，也可以使用字符串为所有表头单元格设置一个固定的 `className` */
  headerCellClassName?: CellCls<any>
  /** 表头单元格的 `style` 的回调方法，也可以使用一个固定的 `Object` 为所有表头单元格设置一样的 `Style` */
  headerCellStyle?: CellStyle<any>
  /** 行数据的 `Key`，用来优化 `Table` 的渲染；在使用 `reserve-selection` 功能与显示树形数据时，该属性是必填的。类型为 `String` 时，支持多层访问：`user.info.id`，但不支持 `user.info[0].id`，此种情况请使用 `Function` */
  rowKey?: string | ((row: any) => string)
  /** 空数据时显示的文本内容， 也可以通过 `#empty` 插槽设置，默认值为 `No Data` */
  emptyText?: string
  /** 是否默认展开所有行，当 `Table` 包含展开行存在或者为树形表格时有效，默认值为 `false` */
  defaultExpandAll?: boolean
  /** 可以通过该属性设置 `Table` 目前的展开行，需要设置 `row-key` 属性才能使用，该属性为展开行的 `keys` 数组 */
  expandRowKeys?: any[]
  /** 默认的排序列的 `prop` 和顺序。它的 `prop` 属性指定默认的排序的列，`order` 指定默认排序的顺序，默认值为 `如果 prop 已配置, 同时 order 未配置, 那么 order 默认为升序` */
  defaultSort?: Sort
  /** `tooltip effect` 属性，默认值为 `dark` */
  tooltipEffect?: Effect
  /** 溢出 tooltip 的选项 参考 https://element-plus.org/zh-CN/component/tooltip.html#attributes  默认值为 `{ enterable: true, placement: 'top', showArrow: true, hideAfter: 200, popperOptions: { strategy: 'fixed' } }` */
  tooltipOptions?: TableOverflowTooltipOptions
  /** 是否在表尾显示合计行，默认值为 `false` */
  showSummary?: boolean
  /** 合计行第一列的文本，默认值为 `合计` */
  sumText?: string
  /** 自定义的合计计算方法 */
  summaryMethod?: SummaryMethod<any>
  // context?: Table<T>;
  /** 合并行或列的计算方法 */
  spanMethod?: (data: { row: any; rowIndex: number; column: TableColumnCtx<any>; columnIndex: number }) =>
    | number[]
    | {
        rowspan: number
        colspan: number
      }
    | undefined
  /** 在多选表格中，当仅有部分行被选中时，点击表头的多选框时的行为。 若为 `true` ，则选中所有行；若为 `false` ，则取消选择所有行，默认值为 `true` */
  selectOnIndeterminate?: boolean
  /** 展示树形数据时，树节点的缩进，默认值为 `16` */
  indent?: number
  /** 是否懒加载子节点数据 */
  lazy?: boolean
  /** 加载子节点数据的函数，`lazy` 为 `true` 时生效，函数第二个参数包含了节点的层级信息 */
  load?: (row: any, treeNode: TreeNode, resolve: (data: any[]) => void) => void
  /** 渲染嵌套数据的配置选项，默认值为 `{ hasChildren: 'hasChildren', children: 'children' }` */
  treeProps?: {
    hasChildren?: string
    children?: string
  }
  /** 设置表格单元、行和列的布局方式，默认值为 `fixed` */
  tableLayout?: Layout
  /** 总是显示滚动条，默认值为 `false` */
  scrollbarAlwaysOn?: boolean
  /** 当内容过长被隐藏时显示 `tooltip`，默认值 `false` */
  showOverflowTooltip?: boolean
  /** 确保主轴的最小尺寸，以便不超过内容，默认值为 `false` */
  flexible?: boolean
}

/**
 * @description `element-plus` 的 `table` 属性，已扩展
 */
export interface TableExtProps extends TableProps {
  /** 唯一键，如果单个页面有多个表格实例，但是您只获取到一个表格实例，设置 `id` 即可解决，不过大多数情况下不需要设置，会自动处理 */
  id?: string | number
  /** `Table-column` 配置 `该属性为必填属性` */
  columns: Array<ColumnExtProp>
  /** 是否开启加载动画，默认值：`false` */
  loading?: boolean
  /** 加载动画的相关配置 */
  loadingConfig?: LoadingConfig
  /** 是否隐藏操作列，默认值：`false` */
  hiddenOperation?: boolean
  /** 是否隐藏翻页 */
  hiddenPagination?: boolean
  /** 分页相关配置 可以考虑使用el中的type 使用Omit<Partial<PaginationProps>, '?'>可以创建一个新的类型，并省略部分属性*/
  pagination?: PaginationProps
  /** 初始化请求参数 */
  initParam?: any
}

export interface Emits {
  (e: 'selection-change', value: any[]): void
  (e: 'size-change', size: string): void
  (e: 'current-change', currentPage: string): void
  (e: 'query-list', initParam: any): void
  (e: 'update', row: any): void
  (e: 'delete', row: any): void
}
