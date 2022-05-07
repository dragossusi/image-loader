package ro.dragossusi.kil.compose

import androidx.compose.runtime.compositionLocalOf
import ro.dragossusi.kil.config.KilConfig

val LocalKilConfig = compositionLocalOf<KilConfig?> {
    null
}