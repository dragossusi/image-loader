package ro.dragossusi.kil.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

fun Kil(
    block: KilComposeConfigBuilder.() -> Unit,
    content: @Composable () -> Unit
) {
    val config = KilComposeConfigBuilder()
        .apply(block)
        .build()
    CompositionLocalProvider(
        LocalKilConfig provides config,
        content = content
    )
}