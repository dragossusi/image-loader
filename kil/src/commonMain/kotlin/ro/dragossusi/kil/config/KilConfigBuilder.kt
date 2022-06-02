package ro.dragossusi.kil.config

import androidx.compose.ui.graphics.ImageBitmap
import io.ktor.client.*
import okio.FileSystem
import okio.Path
import ro.dragossusi.kil.cache.*
import ro.dragossusi.kil.decoder.Decoder
import ro.dragossusi.kil.fetcher.Fetcher
import ro.dragossusi.kil.fetcher.FileFetcher
import ro.dragossusi.kil.fetcher.NetworkFetcher
import ro.dragossusi.kil.files.DefaultFileSystem
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.getOrPut
import kotlin.collections.mapValues
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.plusAssign
import kotlin.collections.set
import kotlin.collections.toList
import kotlin.collections.toMap
import kotlin.reflect.KClass

class KilConfigBuilder constructor(
    val fetchers: MutableMap<KClass<*>, Fetcher<*>> = mutableMapOf(),
    val decoders: MutableMap<KClass<*>, Decoder<*>> = mutableMapOf(),
    private val caches: MutableMap<CacheType, MutableCaches<*>> = mutableMapOf(),
) {

    inline fun <reified T : Any> addFetcher(fetcher: Fetcher<T>) {
        fetchers[T::class] = fetcher
    }

    inline fun <reified T : Any> addDecoder(decoder: Decoder<T>) {
        decoders[T::class] = decoder
    }

    fun <T : Any> addCache(cacheType: CacheType, cache: Cache<Any, T>) {
        cachesFor(cacheType) += cache
    }

    fun build(): KilConfig {
        return DefaultKilConfig(
            fetchers.toMap(),
            decoders.toMap(),
            caches.mapValues { it.value.toList() },
        )
    }

    private fun cachesFor(cacheType: CacheType): MutableList<Cache<Any, *>> {
        return caches.getOrPut(cacheType) { mutableListOf() }
    }

}

fun KilConfigBuilder.ramImageBitmapCache(maxSize: Int = 20) {
    addCache<ImageBitmap>(
        CacheType.MEMORY,
        PlatformCache(maxSize)
    )
}

fun <T : Any> KilConfigBuilder.fileCache(
    dir: Path,
    fileCoder: FileCoder<T>,
    fileSystem: FileSystem = DefaultFileSystem
) {
    addCache(
        cacheType = CacheType.DISK,
        cache = FileCache(
            fileSystem,
            dir,
            fileCoder
        )
    )
}

fun KilConfigBuilder.fileFetcher(fileSystem: FileSystem = DefaultFileSystem) {
    addFetcher(FileFetcher(fileSystem))
}

fun KilConfigBuilder.urlFetcher(client: HttpClient) {
    addFetcher(NetworkFetcher(client))
}