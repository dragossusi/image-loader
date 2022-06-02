package ro.dragossusi.kil.cache

import okio.FileSystem
import okio.Path

class FileCache<K, V>(
    private val fileSystem: FileSystem,
    private val dir: Path,
    private val fileCoder: FileCoder<V>
) : Cache<K, V> {

    override val size: Int
        get() = fileSystem.list(dir).size

    override val maxSize: Int
        get() = Int.MAX_VALUE

    override val cacheType: CacheType
        get() = CacheType.DISK

    override fun clear() {
        fileSystem.deleteRecursively(dir)
    }

    override fun removeKey(key: K) {
        fileSystem.delete(createPath(key))
    }

    override fun contains(key: K): Boolean {
        return fileSystem.exists(createPath(key))
    }

    override fun get(key: K): V? {
        val path = createPath(key)
        if (!fileSystem.exists(path)) return null
        return fileSystem.read(path) {
            fileCoder.decode(source = this)
        }
    }

    override fun set(key: K, value: V) {
        fileSystem.write(createPath(key)) {
            fileCoder.encode(value, this)
        }
    }

    fun createPath(key: K): Path {
        return dir.div(key.toString())
    }
}