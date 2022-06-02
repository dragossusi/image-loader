package ro.dragossusi.kil.compose.decoder

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image
import ro.dragossusi.kil.config.LoadConfig
import ro.dragossusi.kil.decoder.Decoder

object BitmapDecoder : Decoder<ImageBitmap> {

    override suspend fun decode(data: ByteArray, loadConfig: LoadConfig): ImageBitmap {
        //create bitmap from data
        val bitmap = Image.makeFromEncoded(data)
        //todo resize bitmap to loadConfig.size(DpSize)
        return bitmap.toComposeImageBitmap()
    }

}