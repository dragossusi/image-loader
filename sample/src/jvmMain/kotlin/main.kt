import androidx.compose.ui.window.singleWindowApplication
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import ro.dragossusi.ImageSample

fun main() {
    singleWindowApplication {
        ImageSample(HttpClient(CIO))
    }
}