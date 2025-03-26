package com.stuempke.luzuatest.domain

sealed interface Error {
    enum class Remote : Error {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN
    }
}
