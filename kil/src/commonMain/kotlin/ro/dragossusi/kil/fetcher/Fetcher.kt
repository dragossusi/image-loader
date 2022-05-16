package ro.dragossusi.kil.fetcher

import io.ktor.utils.io.*
import kotlinx.coroutines.flow.Flow
import ro.dragossusi.kil.DataSource
import ro.dragossusi.kil.Resource
import ro.dragossusi.kil.config.LoadConfig

interface Fetcher<T : Any> {

    val dataSource: DataSource

    fun canFetch(data: Any): Boolean

    fun fetch(data: Any, loadConfig: LoadConfig): Flow<Resource<ByteArray>>

}