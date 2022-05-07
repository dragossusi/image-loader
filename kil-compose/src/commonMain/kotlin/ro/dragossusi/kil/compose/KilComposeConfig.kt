package ro.dragossusi.kil.compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import io.ktor.client.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import okio.FileSystem
import okio.Path
import ro.dragossusi.kil.Resource
import ro.dragossusi.kil.cache.Cache
import ro.dragossusi.kil.cache.PlatformCache
import ro.dragossusi.kil.config.KilConfig
import ro.dragossusi.kil.config.LoadConfig
import ro.dragossusi.kil.fetcher.Fetcher
import ro.dragossusi.kil.fetcher.FileFetcher
import ro.dragossusi.kil.fetcher.NetworkFetcher
import kotlin.reflect.KClass

class KilComposeConfig(
    override val fetchers: Map<KClass<*>, Fetcher<*>>,
    private val painterCache: Cache<Any, Painter>
) : KilConfig {

    override fun hasCached(data: Any): Boolean {
        return painterCache[data] != null
    }

    fun load(
        data: Any,
        loadConfig: LoadConfig
    ): Flow<Painter> {
        if (loadConfig.dataSource == null) {
            painterCache[data]?.let{
                return flowOf(it)
            }
        }
        val fetcher = fetcherFor(data)
        return fetcher.fetch(data, loadConfig)
            .map {
                when (it) {
                    is Resource.Loading -> {
                        ColorPainter(Color.Gray)
                    }
                    is Resource.Failure -> {
                        ColorPainter(Color.Red)
                    }
                    is Resource.Success -> {

                    }
                }
            }
    }

}