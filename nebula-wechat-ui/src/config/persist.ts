import { PersistedStateOptions } from 'pinia-plugin-persistedstate'

/**
 * @description pinia持久化参数配置
 * @param key 存储到持久化的 name
 * @param paths 需要持久化的 state name 默认持久化全部
 * @param storage 存储位置
 * @return persist
 * */
const piniaPersistConfig = (
  key: string,
  paths: Array<string> | undefined = undefined,
  storage: Storage = window.sessionStorage
): PersistedStateOptions => {
  return {
    key,
    storage,
    paths
  }
}

export default piniaPersistConfig
