package ro.dragossusi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.http.*
import ro.dragossusi.kil.Resource
import ro.dragossusi.kil.compose.KilCompose
import ro.dragossusi.kil.compose.bitmapDecoder
import ro.dragossusi.kil.compose.rememberImagePainter
import ro.dragossusi.kil.compose.rememberKilConfig
import ro.dragossusi.kil.config.LoadConfig
import ro.dragossusi.kil.config.fileFetcher
import ro.dragossusi.kil.config.urlFetcher
import ro.dragossusi.kil.files.DefaultFileSystem

@Composable
fun ImageSample(
    client: HttpClient = HttpClient()
) {
    val config = rememberKilConfig {
        fileFetcher(DefaultFileSystem)
        urlFetcher(client)
        bitmapDecoder()
    }
    KilCompose(config) {
        val painter by rememberImagePainter(
            Url("http://placehold.jp/300x300.png"),
            loadConfig = LoadConfig(
                size = DpSize(32.dp, 32.dp)
            )
        )
        AsyncImage(painter, modifier = Modifier.size(128.dp))
    }
}

@Composable
private fun AsyncImage(
    resource: Resource<Painter>,
    modifier: Modifier = Modifier
) {
    when (resource) {
        is Resource.Success -> {
            Image(
                painter = resource.data,
                contentDescription = null,
                modifier = modifier
            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                modifier = modifier
            )
        }
        is Resource.Failure -> {
            resource.throwable.printStackTrace()
            Text(
                resource.throwable.toString(),
                modifier = modifier
            )
        }
    }
}