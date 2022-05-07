package ro.dragossusi.kil.compose

import io.ktor.client.*
import io.ktor.http.*
import okio.FileSystem
import okio.Path
import ro.dragossusi.kil.cache.PlatformCache
import ro.dragossusi.kil.fetcher.Fetcher
import ro.dragossusi.kil.fetcher.FileFetcher
import ro.dragossusi.kil.fetcher.NetworkFetcher
import kotlin.reflect.KClass

class KilComposeConfigBuilder(
    val fetchers: MutableMap<KClass<*>, Fetcher<*>> = mutableMapOf(),
    var maxCacheSize: Int = 20,
    var fileSystem: FileSystem = DefaultFileSystem,
    var client: HttpClient? = null
) {

    inline fun <reified T : Any> addFetcher(fetcher: Fetcher<T>) {
        fetchers[T::class] = fetcher
    }

    internal fun build(): KilComposeConfig {
        val list = fetchers.toMutableMap()
        list[Path::class] = FileFetcher(fileSystem)
        list[Url::class] = NetworkFetcher(createClient())
        return KilComposeConfig(
            fetchers.toMap(),
            PlatformCache(maxCacheSize),
        )
    }

    private fun createClient(): HttpClient {
        return client ?: HttpClient()
    }
}