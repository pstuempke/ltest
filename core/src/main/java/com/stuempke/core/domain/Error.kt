package com.stuempke.core.domain

sealed interface Error {
    enum class Remote : Error {
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN,
        NOT_FOUND,
        UNAUTHORIZED
    }
}
