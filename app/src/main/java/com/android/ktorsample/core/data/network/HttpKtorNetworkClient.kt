package com.android.ktorsample.core.data.network

import android.util.Log
import com.android.ktorsample.BuildConfig
import com.android.ktorsample.core.data.network.model.Response
import com.android.ktorsample.core.data.network.model.StatusCode
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

abstract class HttpKtorNetworkClient<SealedRequest, SealedResponse> :
    HttpNetworkClient<SealedRequest, SealedResponse> {
    protected val httpClient by lazy {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = TIMEOUT_MILLIS
                connectTimeoutMillis = TIMEOUT_MILLIS
                socketTimeoutMillis = TIMEOUT_MILLIS
            }
        }
    }

    override suspend fun getResponse(sealedRequest: SealedRequest): Response<SealedResponse> {
        return runCatching {
            mapToResponse(
                requestType = sealedRequest,
                httpResponse = sendResponseByType(sealedRequest)
            )
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                Log.v(TAG, "error -> ${error.localizedMessage}")
                error.printStackTrace()
            }
        }.getOrNull() ?: Response()
    }

    protected abstract suspend fun sendResponseByType(request: SealedRequest): HttpResponse

    protected abstract suspend fun getResponseBodyByRequestType(
        requestType: SealedRequest,
        httpResponse: HttpResponse
    ): SealedResponse

    private suspend fun mapToResponse(
        requestType: SealedRequest,
        httpResponse: HttpResponse
    ): Response<SealedResponse> {
        Log.v(TAG, "body = ${httpResponse.bodyAsText()}")
        return if (httpResponse.status.isSuccess()) {
            Response(
                isSuccess = true,
                resultCode = StatusCode(httpResponse.status.value),
                body = getResponseBodyByRequestType(requestType, httpResponse)
            )
        } else {
            Response(
                isSuccess = false,
                resultCode = StatusCode(httpResponse.status.value)
            )
        }
    }

    private companion object {
        const val TIMEOUT_MILLIS = 10_000L
        val TAG = HttpKtorNetworkClient::class.simpleName ?: "HttpKtorNetworkClient"
    }
}