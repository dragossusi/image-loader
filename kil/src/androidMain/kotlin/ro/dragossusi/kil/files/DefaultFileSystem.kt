package ro.dragossusi.kil.files

import okio.FileSystem

actual val DefaultFileSystem: FileSystem
    get() = FileSystem.SYSTEM