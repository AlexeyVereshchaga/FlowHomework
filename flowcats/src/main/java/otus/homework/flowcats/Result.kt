package otus.homework.flowcats

sealed class Result {
    data class Success<T>(val data: T) : Result()
    data class Error(val text: String) : Result()
}