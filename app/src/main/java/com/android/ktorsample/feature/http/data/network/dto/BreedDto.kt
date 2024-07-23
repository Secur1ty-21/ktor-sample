package com.android.ktorsample.feature.http.data.network.dto

import com.android.ktorsample.feature.http.domain.model.Breed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BreedDto(
    @SerialName("breed")
    val breed: String,
    @SerialName("country")
    val country: String,
    @SerialName("origin")
    val origin: String,
    @SerialName("coat")
    val coat: String,
    @SerialName("pattern")
    val pattern: String
)

fun BreedDto.mapToDomain(): Breed {
    return Breed(
        breed = breed,
        country = country,
        origin = origin,
        coat = coat,
        pattern = pattern
    )
}