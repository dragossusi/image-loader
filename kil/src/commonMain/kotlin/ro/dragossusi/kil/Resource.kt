package ro.dragossusi.kil

sealed class Resource<out T> {
    data class Loading(val progress: Float) : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Failure(val throwable: Throwable) : Resource<Nothing>()
}