package ro.dragossusi.kil.compose

import okio.FileSystem
import okio.NodeJsFileSystem

actual val DefaultFileSystem: FileSystem
    get() = NodeJsFileSystem