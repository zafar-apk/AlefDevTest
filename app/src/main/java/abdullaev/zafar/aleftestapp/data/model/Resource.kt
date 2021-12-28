package abdullaev.zafar.aleftestapp.data.model

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    object Loading : Resource<Nothing>()
    class Error(message: String) : Resource<Nothing>()
}