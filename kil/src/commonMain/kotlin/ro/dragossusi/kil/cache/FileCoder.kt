package ro.dragossusi.kil.cache

import okio.BufferedSink
import okio.BufferedSource

interface FileCoder<T> {
    fun decode(source: BufferedSource): T
    fun encode(value: T, sink: BufferedSink)
}