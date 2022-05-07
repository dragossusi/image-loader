package ro.dragossusi.kil.config

import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import ro.dragossusi.kil.DataSource
import kotlin.coroutines.CoroutineContext

data class LoadConfig(
    val requestData: HttpRequestData? = null,
    val coroutineContext: CoroutineContext = Dispatchers.Default,
    val dataSource: DataSource? = null
)