package ro.dragossusi.kil.fetcher

import io.ktor.utils.io.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.FileSystem
import okio.Path
import okio.use
import ro.dragossusi.kil.DataSource
import ro.dragossusi.kil.Resource
import ro.dragossusi.kil.config.LoadConfig
import ro.dragossusi.kil.util.readChannel

class FileFetcher(
    private val fileSystem: FileSystem
) : Fetcher<Path> {
    override val dataSource: DataSource
        get() = DataSource.Disk

    override fun canFetch(data: Any): Boolean {
        return data is Path && fileSystem.exists(data)
    }

    override fun fetch(data: Any, loadConfig: LoadConfig): Flow<Resource<ByteReadChannel>> = flow {
        if (!canFetch(data)) {
            emit(Resource.Failure(IllegalArgumentException()))
            return@flow
        }
        emit(Resource.Loading(0f))
        try {
            emit(Resource.Success(fileSystem.readChannel(data as Path)))
        } catch (e: CancellationException) {
            throw e
        } catch (t: Throwable) {
            emit(Resource.Failure(t))
        }
    }
}