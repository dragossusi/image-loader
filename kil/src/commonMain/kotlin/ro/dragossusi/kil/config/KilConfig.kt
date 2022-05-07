package ro.dragossusi.kil.config

import ro.dragossusi.kil.fetcher.Fetcher
import kotlin.reflect.KClass

interface KilConfig {
    val fetchers: Map<KClass<*>, Fetcher<*>>

    fun hasCached(data: Any): Boolean

    fun fetcherFor(data: Any): Fetcher<*>? {
        return fetchers.entries.firstOrNull {
            it.key == data::class
                    && it.value.canFetch(data)
        }?.value
    }
}