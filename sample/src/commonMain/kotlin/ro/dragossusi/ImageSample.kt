package ro.dragossusi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.http.*
import okio.Path.Companion.toPath
import ro.dragossusi.kil.DataSource
import ro.dragossusi.kil.Resource
import ro.dragossusi.kil.compose.*
import ro.dragossusi.kil.config.LoadConfig
import ro.dragossusi.kil.config.fileFetcher
import ro.dragossusi.kil.config.ramImageBitmapCache
import ro.dragossusi.kil.config.urlFetcher
import ro.dragossusi.kil.files.DefaultFileSystem


private val imageUrl = Url("http://placehold.jp/300x300.png")

@Composable
fun ImageSample(
    client: HttpClient = HttpClient()
) {
    val config = rememberKilConfig {
        fileFetcher(DefaultFileSystem)
        urlFetcher(client)
        bitmapDecoder()
        ramImageBitmapCache()
        filePainterCache("cache".toPath())
    }
    var loadFromMemory by remember {
        mutableStateOf(false)
    }
    KilCompose(config) {
        val painter by rememberImagePainter(imageUrl)
        Column {
            AsyncImage(painter, modifier = Modifier.size(128.dp))

            Button(
                onClick = { loadFromMemory = true }
            ) {
                Text("Load from memory")
            }

            if (loadFromMemory) {
                val painter by rememberImagePainter(
                    imageUrl,
                    loadConfig = LoadConfig(
                        dataSource = DataSource.Memory
                    )
                )
                AsyncImage(painter, modifier = Modifier.size(128.dp))
            }
        }
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