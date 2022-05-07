package ro.dragossusi.kil.decoder

import io.ktor.utils.io.*
import ro.dragossusi.kil.config.LoadConfig

interface Decoder<T> {

    suspend fun decode(
        channel: ByteReadChannel,
        loadConfig: LoadConfig
    ): T

}