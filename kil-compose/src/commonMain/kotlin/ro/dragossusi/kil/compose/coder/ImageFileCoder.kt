package ro.dragossusi.kil.compose.coder

import okio.BufferedSink
import okio.BufferedSource
import ro.dragossusi.kil.cache.FileCoder

class ImageFileCoder : FileCoder<ByteArray> {

    override fun decode(source: BufferedSource): ByteArray {
        return source.readByteArray()
    }

    override fun encode(value: ByteArray, sink: BufferedSink) {
        sink.write(value)
    }

}