package com.android.ktorsample.feature.websocket.data.network

import com.android.ktorsample.core.data.network.WebsocketKtorNetworkClient
import com.android.ktorsample.feature.websocket.data.network.dto.MessageDto
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WebsocketKtorNetworkClientImpl: WebsocketKtorNetworkClient<MessageDto, MessageDto>() {
    override val baseUrl = "wss://echo.websocket.org"
    override fun mapIncomingMessage(message: Frame.Text, converter: Json): MessageDto {
        return runCatching {
            converter.decodeFromString<MessageDto>(message.readText())
        }.getOrNull() ?: MessageDto(message.readText())
    }

    override fun mapSentMessageToJson(message: MessageDto, converter: Json): String {
        return converter.encodeToString(message)
    }
}