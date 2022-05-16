package ro.dragossusi.kil.config

import androidx.compose.ui.graphics.painter.Painter
import okio.Path
import ro.dragossusi.kil.cache.Cache
import ro.dragossusi.kil.decoder.Decoder
import ro.dragossusi.kil.fetcher.Fetcher
import kotlin.reflect.KClass

class DefaultKilConfig(
    override val fetchers: Map<KClass<*>, Fetcher<*>>,
    override val decoders: Map<KClass<*>, Decoder<*>>,
    private val painterCache: Cache<Any, Painter>
) : KilConfig {


    override fun hasCached(data: Any): Boolean {
        return painterCache[data] != null
    }

    override fun cachedPainter(data: Any): Painter? {
        return painterCache[data]
    }
}