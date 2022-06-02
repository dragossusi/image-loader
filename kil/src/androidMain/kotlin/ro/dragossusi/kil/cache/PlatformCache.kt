package ro.dragossusi.kil.cache

import android.util.LruCache

actual class PlatformCache<K, V> actual constructor(
    maxSize: Int
) : LruCache<K, V>(maxSize), Cache<K, V> {

    override val cacheType: CacheType
        get() = CacheType.MEMORY

    override val size: Int
        get() = size()

    override val maxSize: Int
        get() = maxSize()

    override fun contains(key: K): Boolean {
        return get(key) != null
    }

    override fun removeKey(key: K) {
        remove(key)
    }

    override fun clear() {
        evictAll()
    }

    override fun set(key: K, value: V) {
        put(key, value)
    }

}