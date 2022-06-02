package ro.dragossusi.kil.compose

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import kotlinx.coroutines.flow.*
import ro.dragossusi.kil.DataSource
import ro.dragossusi.kil.KilExperimental
import ro.dragossusi.kil.Resource
import ro.dragossusi.kil.cache.CacheType
import ro.dragossusi.kil.config.*
import ro.dragossusi.kil.error.DataNotFoundException

@OptIn(KilExperimental::class)
fun KilConfig.loadPainter(
    data: Any,
    loadConfig: LoadConfig
): Flow<Resource<Painter>> {
    return when (loadConfig.dataSource) {
        DataSource.Memory -> loadFromCache(data, loadConfig, CacheType.MEMORY)
        DataSource.Disk -> loadFromCache(data, loadConfig, CacheType.DISK)
        DataSource.Network -> loadFromNetwork(data, loadConfig)
        null -> {
            loadFromCache(data, loadConfig, CacheType.DISK)
                .flatMapConcat {
                    if (it is Resource.Failure) {
                        loadFromCache(data, loadConfig, CacheType.MEMORY)
                    } else flowOf(it)
                }
                .flatMapConcat {
                    if (it is Resource.Failure) {
                        loadFromNetwork(data, loadConfig)
                    } else flowOf(it)
                }
        }
    }
}

private fun KilConfig.loadFromCache(
    data: Any,
    loadConfig: LoadConfig,
    cacheType: CacheType
): Flow<Resource<Painter>> = flow {
    val caches = cachesFor<ImageBitmap>(cacheType)
    val cached = caches?.find {
        it.contains(data)
    }?.get(data)

    if (cached == null) {
        emit(Resource.Failure(DataNotFoundException(data.toString())))
        return@flow
    }
    emit(Resource.Success(BitmapPainter(cached)))
}

private fun KilConfig.loadFromNetwork(
    data: Any,
    loadConfig: LoadConfig
): Flow<Resource<Painter>> {
    val fetcher = requireFetcherFor(data)
    val decoder = requireDecoderFor<ImageBitmap>()
    val caches = requireCachesFor<ImageBitmap>(CacheType.MEMORY)

    return fetcher.fetch(data, loadConfig)
        .map { resource ->
            resource.map { bytes ->
                decoder.decode(bytes, loadConfig).let { bitmap ->
                    caches.forEach { cache ->
                        cache[data] = bitmap
                    }
                    BitmapPainter(bitmap)
                }
            }
        }
}