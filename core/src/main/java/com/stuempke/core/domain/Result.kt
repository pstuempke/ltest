package com.stuempke.core.domain


sealed interface Result<out D, out E : Error> {
    fun getOrNull(): D? {
        return when (this) {
            is Success -> data
            is Error -> null
        }
    }

    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.stuempke.core.domain.Error>(val error: E) :
        Result<Nothing, E>
}


inline fun <T, E : Error, R> Result<List<T>, E>.mapList(transform: (T) -> R): Result<List<R>, E> =
    this.map { list -> list.map(transform) }

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

inline fun <T, E : Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            action(error)
            this
        }

        is Result.Success -> this
    }
}

typealias EmptyResult<E> = Result<Unit, E>