package ro.dragossusi.kil.config

import ro.dragossusi.kil.cache.Cache
import ro.dragossusi.kil.cache.CacheType
import ro.dragossusi.kil.cache.Caches
import ro.dragossusi.kil.decoder.Decoder
import ro.dragossusi.kil.error.DataNotFoundException
import ro.dragossusi.kil.fetcher.Fetcher
import kotlin.reflect.KClass

interface KilConfig {

    val fetchers: Map<KClass<*>, Fetcher<*>>
    val decoders: Map<KClass<*>, Decoder<*>>
    val caches: Map<CacheType, Caches<*>>

    fun hasCached(data: Any, cacheType: CacheType): Boolean

}

inline fun <reified T : Any> KilConfig.decoderFor(): Decoder<T>? {
    return decoders[T::class] as Decoder<T>?
}

inline fun <reified T : Any> KilConfig.requireDecoderFor(): Decoder<T> {
    return decoderFor() ?: throw DataNotFoundException("Decoder for ${T::class.simpleName}")
}

fun KilConfig.fetcherFor(data: Any): Fetcher<*>? {
    return fetchers[data::class]?.takeIf {
        it.canFetch(data)
    }
}

fun KilConfig.requireFetcherFor(data: Any): Fetcher<*> {
    return fetcherFor(data) ?: throw DataNotFoundException("Decoder for $data")
}

inline fun <reified T : Any> KilConfig.cachesFor(
    cacheType: CacheType
): Caches<T>? {
    return caches[cacheType]?.let {
        it as Caches<T>
    }
}

inline fun <reified T : Any> KilConfig.requireCachesFor(
    cacheType: CacheType
): Caches<T> {
    return cachesFor(cacheType) ?: throw DataNotFoundException("Caches for $cacheType")
}