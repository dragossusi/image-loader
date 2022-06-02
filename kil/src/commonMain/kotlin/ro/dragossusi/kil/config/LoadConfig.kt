package ro.dragossusi.kil.config

import androidx.compose.ui.unit.DpSize
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import ro.dragossusi.kil.DataSource
import ro.dragossusi.kil.KilExperimental
import kotlin.coroutines.CoroutineContext

data class LoadConfig(
    val requestData: HttpRequestData? = null,
    val coroutineContext: CoroutineContext = Dispatchers.Default,
    @property:KilExperimental val dataSource: DataSource? = null,
    @property:KilExperimental val size: DpSize? = null,
    @property:KilExperimental val cacheOnDisk: Boolean = false
)