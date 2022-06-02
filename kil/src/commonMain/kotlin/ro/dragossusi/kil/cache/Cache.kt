package ro.dragossusi.kil.cache

import ro.dragossusi.kil.DataSource

interface Cache<K, V> {
    val size: Int
    val maxSize: Int

    val cacheType: CacheType

    fun clear()

    fun removeKey(key: K)

    fun contains(key: K): Boolean

    operator fun get(key: K): V?
    operator fun set(key: K, value: V)

    operator fun minusAssign(key: K) {
        removeKey(key)
    }
}