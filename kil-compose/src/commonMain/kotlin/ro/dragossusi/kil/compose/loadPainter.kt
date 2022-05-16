package ro.dragossusi.kil.compose

import androidx.compose.ui.graphics.painter.Painter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ro.dragossusi.kil.Resource
import ro.dragossusi.kil.config.KilConfig
import ro.dragossusi.kil.config.LoadConfig
import ro.dragossusi.kil.config.decoderFor

fun KilConfig.loadPainter(
    data: Any,
    loadConfig: LoadConfig
): Flow<Resource<Painter>> {
    cachedPainter(data)?.let {
        return flowOf(Resource.Success(it))
    }
    val fetcher = fetcherFor(data)!!
    val decoder = decoderFor<Painter>()!!
    return fetcher.fetch(data, loadConfig)
        .map {
            it.map { bytes ->
                decoder.decode(bytes, loadConfig)
            }
        }
}