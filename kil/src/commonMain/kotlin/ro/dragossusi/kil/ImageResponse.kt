package ro.dragossusi.kil

import io.ktor.utils.io.*

sealed class ImageResponse {

    data class Success(
        val channel:ByteReadChannel
    ): ImageResponse()

    data class Failure(
        val throwable: Throwable
    ): ImageResponse()
}