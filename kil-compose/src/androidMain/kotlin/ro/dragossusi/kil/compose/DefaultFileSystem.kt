package ro.dragossusi.kil.compose

import okio.FileSystem

actual val DefaultFileSystem: FileSystem
    get() = FileSystem.SYSTEM