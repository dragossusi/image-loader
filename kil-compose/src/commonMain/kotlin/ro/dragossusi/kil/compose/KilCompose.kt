package ro.dragossusi.kil.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import okio.Path
import ro.dragossusi.kil.compose.coder.ImageFileCoder
import ro.dragossusi.kil.compose.decoder.BitmapDecoder
import ro.dragossusi.kil.config.KilConfig
import ro.dragossusi.kil.config.KilConfigBuilder
import ro.dragossusi.kil.config.fileCache

@Composable
fun rememberKilConfig(
    block: KilConfigBuilder.() -> Unit = {}
): KilConfig = KilConfigBuilder().apply(block).build()

@Composable
fun KilCompose(
    kilConfig: KilConfig,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalKilConfig provides kilConfig,
        content = content
    )
}

fun KilConfigBuilder.bitmapDecoder() {
    addDecoder(BitmapDecoder)
}

fun KilConfigBuilder.filePainterCache(
    dir: Path
) {
    fileCache(
        dir = dir,
        fileCoder = ImageFileCoder()
    )
}