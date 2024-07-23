package com.android.ktorsample.feature.http.data.network.dto

import com.android.ktorsample.feature.http.domain.model.RandomCatFact
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RandomCatFactDto(
    @SerialName("fact")
    val fact: String
)

fun RandomCatFactDto.mapToDomain(): RandomCatFact {
    return RandomCatFact(fact = fact)
}