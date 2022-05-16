package ro.dragossusi.kil.config

import io.ktor.client.*
import io.ktor.http.*
import okio.FileSystem
import ro.dragossusi.kil.cache.PlatformCache
import ro.dragossusi.kil.decoder.Decoder
import ro.dragossusi.kil.fetcher.Fetcher
import ro.dragossusi.kil.fetcher.FileFetcher
import ro.dragossusi.kil.fetcher.NetworkFetcher
import ro.dragossusi.kil.files.DefaultFileSystem
import kotlin.reflect.KClass

class KilConfigBuilder(
    val fetchers: MutableMap<KClass<*>, Fetcher<*>> = mutableMapOf(),
    val decoders: MutableMap<KClass<*>, Decoder<*>> = mutableMapOf(),
    var maxCacheSize: Int = 20,
    var client: HttpClient? = null
) {

    inline fun <reified T : Any> addFetcher(fetcher: Fetcher<T>) {
        fetchers[T::class] = fetcher
    }

    inline fun <reified T : Any> addDecoder(decoder: Decoder<T>) {
        decoders[T::class] = decoder
    }

    fun build(): KilConfig {
        val list = fetchers.toMutableMap()
        list[Url::class] = NetworkFetcher(createClient())
        return DefaultKilConfig(
            fetchers.toMap(),
            decoders.toMap(),
            PlatformCache(maxCacheSize),
        )
    }

    private fun createClient(): HttpClient {
        return client ?: HttpClient()
    }
}

fun KilConfigBuilder.fileFetcher(fileSystem: FileSystem = DefaultFileSystem) {
    addFetcher(FileFetcher(fileSystem))
}

fun KilConfigBuilder.urlFetcher(client: HttpClient) {
    addFetcher(NetworkFetcher(client))
}