package ro.dragossusi.kil.config

import androidx.compose.ui.graphics.painter.Painter
import ro.dragossusi.kil.cache.Cache
import ro.dragossusi.kil.cache.CacheType
import ro.dragossusi.kil.decoder.Decoder
import ro.dragossusi.kil.fetcher.Fetcher
import kotlin.reflect.KClass

class DefaultKilConfig(
    override val fetchers: Map<KClass<*>, Fetcher<*>>,
    override val decoders: Map<KClass<*>, Decoder<*>>,
    override val caches: Map<CacheType, List<Cache<Any, *>>>
) : KilConfig {

    override fun hasCached(data: Any, cacheType: CacheType): Boolean {
        return caches[cacheType]?.find {
            it.contains(data)
        } != null
    }

}