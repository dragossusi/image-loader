import io.ktor.client.*
import io.ktor.client.engine.js.*
import org.jetbrains.compose.web.renderComposable
import ro.dragossusi.ImageSample

fun main() {
    println("asdf")
    renderComposable(rootElementId = "root") {
        println("asdf")
        ImageSample(HttpClient(Js))
    }
}