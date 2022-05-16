package ro.dragossusi.kil.compose.decoder

import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import ro.dragossusi.kil.config.LoadConfig
import ro.dragossusi.kil.decoder.Decoder

object PainterDecoder : Decoder<Painter> {

    override suspend fun decode(data: ByteArray, loadConfig: LoadConfig): BitmapPainter {
        //create bitmap from data
        val bitmap = Image.makeFromEncoded(data)
        //todo resize bitmap to loadConfig.size(DpSize)
        return BitmapPainter(bitmap.toComposeImageBitmap())
    }

}