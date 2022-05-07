package ro.dragossusi.kil.cache

actual class PlatformCache<K, V> actual constructor(
    override val maxSize: Int
) : Cache<K, V> {

    private val cache: MutableMap<K, V> = LinkedHashMap<K, V>(maxSize, 0.75f)

    override val size: Int
        get() = cache.size

    override fun clear() {
        cache.clear()
    }

    override fun remove(key: K): V? {
        return cache.remove(key)
    }

    override fun get(key: K): V? {
        return cache[key]
    }

    override fun set(key: K, value: V) {
        return cache.set(key, value)
    }

}