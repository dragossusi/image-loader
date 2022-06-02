package ro.dragossusi.kil.compose.decoder

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import ro.dragossusi.kil.config.LoadConfig
import ro.dragossusi.kil.decoder.Decoder

actual object BitmapDecoder : Decoder<ImageBitmap> {
    override suspend fun decode(data: ByteArray, loadConfig: LoadConfig): ImageBitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size).asImageBitmap()
    }
}