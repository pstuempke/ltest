package com.stuempke.luzuatest.data

import com.stuempke.luzuatest.domain.Error
import com.stuempke.luzuatest.domain.Result
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import timber.log.Timber

suspend inline fun <T> safeCall(
    crossinline block: suspend () -> T
): Result<T, Error.Remote> {
    return try {
        Result.Success(block())
    } catch (e: ClientRequestException) {
        Timber.e(e, "Client error: ${e.response.status}")
        Result.Error(Error.Remote.SERVER)
    } catch (e: ServerResponseException) {
        Timber.e(e, "Server error: ${e.response.status}")
        Result.Error(Error.Remote.SERVER)
    } catch (e: ResponseException) {
        Timber.e(e, "Response error: ${e.response.status}")
        Result.Error(Error.Remote.SERVER)
    } catch (e: java.net.UnknownHostException) {
        Timber.e(e, "Network error: Unable to resolve host")
        Result.Error(Error.Remote.NO_INTERNET)
    } catch (e: Exception) {
        Timber.e(e, "Unexpected error")
        Result.Error(Error.Remote.UNKNOWN)
    }
}
