package com.android.ktorsample.feature.http.data.network

import com.android.ktorsample.feature.http.data.network.dto.BreedDto
import com.android.ktorsample.feature.http.data.network.dto.RandomCatFactDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface CatFactResponse {
    class RandomCatFact(val value: RandomCatFactDto) : CatFactResponse
    @Serializable
    class BreedList(
        @SerialName("data")
        val value: List<BreedDto>
    ) : CatFactResponse
}