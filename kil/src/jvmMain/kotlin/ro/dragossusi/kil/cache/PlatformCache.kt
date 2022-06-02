package ro.dragossusi.kil.cache

actual class PlatformCache<K, V> actual constructor(
    override val maxSize: Int
) : Cache<K, V> {

    private val map: MutableMap<K, V> = object : LinkedHashMap<K, V>(maxSize, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean = size > maxSize
    }

    override val size: Int
        get() = map.size

    override val cacheType: CacheType
        get() = CacheType.MEMORY

    override fun clear() {
        map.clear()
    }

    override fun removeKey(key: K) {
        map.remove(key)
    }

    override fun contains(key: K): Boolean {
        return map.contains(key)
    }

    override fun get(key: K): V? {
        return map[key]
    }

    override fun set(key: K, value: V) {
        map[key] = value
    }
}