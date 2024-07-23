package com.android.ktorsample.feature.websocket.data.network.dto

import com.android.ktorsample.feature.websocket.domain.model.Message
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MessageDto(
    @SerialName("message")
    val text: String
)

fun Message.mapToData(): MessageDto {
    return MessageDto(text = text)
}

fun MessageDto.mapToDomain(): Message {
    return Message(text = text)
}