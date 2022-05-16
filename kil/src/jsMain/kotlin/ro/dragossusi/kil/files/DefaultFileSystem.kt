package ro.dragossusi.kil.files

import okio.FileSystem
import okio.NodeJsFileSystem

actual val DefaultFileSystem: FileSystem
    get() = NodeJsFileSystem