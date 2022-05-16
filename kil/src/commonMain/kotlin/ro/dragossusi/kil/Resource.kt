package ro.dragossusi.kil

sealed class Resource<out T> {

    inline fun <R> map(
        block: (T) -> R
    ): Resource<R> {
        return when (this) {
            is Loading -> this
            is Failure -> this
            is Success -> Success(block(this.data))
        }
    }

    data class Loading(val progress: Float) : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Failure(val throwable: Throwable) : Resource<Nothing>()
}