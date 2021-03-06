package ro.dragossusi.kil.decoder

import ro.dragossusi.kil.config.LoadConfig

interface Decoder<R> {

    suspend fun decode(
        data: ByteArray,
        loadConfig: LoadConfig
    ): R

}