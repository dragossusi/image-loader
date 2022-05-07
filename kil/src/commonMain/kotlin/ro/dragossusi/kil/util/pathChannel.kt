package ro.dragossusi.kil.util

import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import io.ktor.utils.io.core.internal.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okio.*
import kotlin.coroutines.CoroutineContext

fun FileSystem.readChannel(
    path: Path,
    start: Long = 0,
    coroutineContext: CoroutineContext = Dispatchers.Default
): ByteReadChannel {
    return CoroutineScope(coroutineContext)
        .writer(
            CoroutineName("file-reader") + coroutineContext,
            autoFlush = false
        ) {
            require(start >= 0L) { "start position shouldn't be negative but it is $start" }

            read(path) {
                if (start > 0) {
                    skip(start)
                }
                channel.writeFully(readByteArray())
            }
        }.channel
}