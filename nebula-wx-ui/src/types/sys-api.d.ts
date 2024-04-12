export interface ApiResponse<T> {
  success: boolean
  code: number
  msg: string
  data: T
}

export interface Pageable<T> {
  records: Array<T>
  pageNumber: string
  pageSize: string
  totalPage?: string
  totalRow: string
}
