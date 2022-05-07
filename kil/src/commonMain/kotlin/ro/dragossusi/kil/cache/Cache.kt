package ro.dragossusi.kil.cache

interface Cache<K, V> {
    val size: Int
    val maxSize: Int

    fun clear()

    fun remove(key: K): V?

    operator fun get(key: K): V?
    operator fun set(key: K, value: V)

    operator fun minusAssign(key: K) {
        remove(key)
    }
}