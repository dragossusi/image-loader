package ro.dragossusi.kil.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import ro.dragossusi.kil.Resource
import ro.dragossusi.kil.config.LoadConfig

@Composable
fun rememberImagePainter(
    data: Any,
    key: Any? = data,
    loadConfig: LoadConfig = LoadConfig()
): State<Resource<Painter>> {
    val config = LocalKilConfig.current!!
    return remember(data, key) {
        config.loadPainter(data, loadConfig)
    }.collectAsState(Resource.Loading(0f))
}