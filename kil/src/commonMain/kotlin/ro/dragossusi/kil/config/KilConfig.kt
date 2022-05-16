package ro.dragossusi.kil.config

import androidx.compose.ui.graphics.painter.Painter
import ro.dragossusi.kil.decoder.Decoder
import ro.dragossusi.kil.fetcher.Fetcher
import kotlin.reflect.KClass

interface KilConfig {

    val fetchers: Map<KClass<*>, Fetcher<*>>
    val decoders: Map<KClass<*>, Decoder<*>>

    fun hasCached(data: Any): Boolean

    fun cachedPainter(data: Any): Painter?

    fun fetcherFor(data: Any): Fetcher<*>? {
        return fetchers.entries.firstOrNull {
            it.key == data::class
                    && it.value.canFetch(data)
        }?.value
    }


}

inline fun <reified T : Any> KilConfig.decoderFor(): Decoder<T>? {
    return decoders.entries.firstOrNull {
        it.key == T::class
    }?.value as Decoder<T>?
}