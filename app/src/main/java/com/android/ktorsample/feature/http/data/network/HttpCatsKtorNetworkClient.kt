package com.android.ktorsample.feature.http.data.network

import com.android.ktorsample.core.data.network.HttpKtorNetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path

class HttpCatsKtorNetworkClient : HttpKtorNetworkClient<CatFactRequest, CatFactResponse>() {
    override suspend fun sendResponseByType(request: CatFactRequest): HttpResponse {
        return httpClient.get(BASE_URL) {
            when (request) {
                is CatFactRequest.RandomCatFact -> {
                    url {
                        path("fact")
                    }
                }

                is CatFactRequest.BreedList -> {
                    url {
                        path("breeds")
                        parameter("limit", request.limit)
                    }
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: CatFactRequest,
        httpResponse: HttpResponse
    ): CatFactResponse {
        return when (requestType) {
            is CatFactRequest.RandomCatFact -> {
                CatFactResponse.RandomCatFact(httpResponse.body())
            }

            is CatFactRequest.BreedList -> {
                httpResponse.body<CatFactResponse.BreedList>()
            }
        }
    }

    private companion object {
        const val BASE_URL = "https://catfact.ninja/"
    }
}