package ro.dragossusi.kil.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ro.dragossusi.kil.compose.decoder.PainterDecoder
import ro.dragossusi.kil.config.KilConfig
import ro.dragossusi.kil.config.KilConfigBuilder

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
    addDecoder(PainterDecoder)
}