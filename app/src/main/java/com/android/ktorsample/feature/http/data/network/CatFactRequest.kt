package com.android.ktorsample.feature.http.data.network

sealed interface CatFactRequest {
    data object RandomCatFact : CatFactRequest

    class BreedList(
        val limit: Int = 10
    ) : CatFactRequest
}