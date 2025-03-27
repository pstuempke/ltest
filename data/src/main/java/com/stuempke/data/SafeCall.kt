package com.stuempke.data

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.serialization.ContentConvertException
import io.ktor.serialization.JsonConvertException
import timber.log.Timber
import java.net.ConnectException

suspend inline fun <T> safeCall(
    crossinline block: suspend () -> T
): com.stuempke.core.domain.Result<T, com.stuempke.core.domain.Error.Remote> {
    return try {
        com.stuempke.core.domain.Result.Success(block())
    } catch (e: ClientRequestException) {
        Timber.e(e, "Client error: ${e.response.status}")
        com.stuempke.core.domain.Result.Error(com.stuempke.core.domain.Error.Remote.SERVER)
    } catch (e: ServerResponseException) {
        Timber.e(e, "Server error: ${e.response.status}")
        com.stuempke.core.domain.Result.Error(com.stuempke.core.domain.Error.Remote.SERVER)
    } catch (e: ResponseException) {
        Timber.e(e, "Response error: ${e.response.status}")
        // Extract from the response whatever we need. Status code, headers, etc.
        val statusCode = e.response.status
        val error = when (statusCode.value) {
            401 -> com.stuempke.core.domain.Error.Remote.UNAUTHORIZED
            404 -> com.stuempke.core.domain.Error.Remote.NOT_FOUND
            else -> com.stuempke.core.domain.Error.Remote.SERVER
        }
        com.stuempke.core.domain.Result.Error(error)
    } catch (e: java.net.UnknownHostException) {
        Timber.e(e, "Network error: Unable to resolve host")
        com.stuempke.core.domain.Result.Error(com.stuempke.core.domain.Error.Remote.NO_INTERNET)
    } catch (e: JsonConvertException) {
        Timber.e(e, "Serialization error")
        com.stuempke.core.domain.Result.Error(com.stuempke.core.domain.Error.Remote.SERIALIZATION)
    } catch (e: Exception) {
        Timber.e(e, "Unexpected error")
        com.stuempke.core.domain.Result.Error(com.stuempke.core.domain.Error.Remote.UNKNOWN)
    }
}
