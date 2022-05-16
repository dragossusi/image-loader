package ro.dragossusi

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.material.Text
import io.ktor.client.*
import io.ktor.http.*
import ro.dragossusi.kil.Resource
import ro.dragossusi.kil.compose.KilCompose
import ro.dragossusi.kil.compose.bitmapDecoder
import ro.dragossusi.kil.files.DefaultFileSystem
import ro.dragossusi.kil.compose.rememberImagePainter
import ro.dragossusi.kil.compose.rememberKilConfig
import ro.dragossusi.kil.config.fileFetcher
import ro.dragossusi.kil.config.urlFetcher

@Composable
fun ImageSample(client: HttpClient = HttpClient()) {
    val config = rememberKilConfig {
        fileFetcher(DefaultFileSystem)
        urlFetcher(client)
        bitmapDecoder()
    }
    KilCompose(config) {
        val painter by rememberImagePainter(
            Url("http://placehold.jp/150x150.png")
        )
        AsyncImage(painter)
    }
}

@Composable
private fun AsyncImage(resource: Resource<Painter>) {
    when (resource) {
        is Resource.Success -> {
            Image(
                painter = resource.data,
                contentDescription = null
            )
        }
        is Resource.Loading -> {
            Text(resource.progress.toString())
        }
        is Resource.Failure -> {
            Text(resource.throwable.toString())
        }
    }
}