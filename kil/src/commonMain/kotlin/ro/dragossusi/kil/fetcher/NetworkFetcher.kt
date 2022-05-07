package ro.dragossusi.kil.fetcher

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import ro.dragossusi.kil.DataSource
import ro.dragossusi.kil.Resource
import ro.dragossusi.kil.config.LoadConfig

class NetworkFetcher(
    private val client: HttpClient
) : Fetcher<Url> {

    override val dataSource: DataSource
        get() = DataSource.Network

    override fun canFetch(data: Any): Boolean {
        return data is Url &&
                (data.protocol.name == "http" || data.protocol.name == "https")
    }

    override fun fetch(data: Any, loadConfig: LoadConfig): Flow<Resource<ByteReadChannel>> = channelFlow {
        if (!canFetch(data)) {
            send(Resource.Failure(IllegalArgumentException()))
            return@channelFlow
        }
        val response = client.request {
            onDownload { bytesSentTotal, contentLength ->
                val progress = (bytesSentTotal.toFloat() / contentLength).coerceAtMost(1.0F)
                send(Resource.Loading(progress))
            }
            loadConfig.requestData?.let(::takeFrom)
            url(data as Url)
        }
        val bytes = response.bodyAsChannel()
        send(Resource.Success(bytes))
    }
}