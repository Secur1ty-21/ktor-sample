package com.android.ktorsample.feature.websocket.domain.impl

import com.android.ktorsample.feature.websocket.domain.api.WebsocketInteractor
import com.android.ktorsample.feature.websocket.domain.api.WebsocketRepository
import com.android.ktorsample.feature.websocket.domain.model.Message
import kotlinx.coroutines.flow.Flow

class WebsocketInteractorImpl(
    private val websocketRepository: WebsocketRepository
): WebsocketInteractor {
    override fun subscribe(): Flow<Message> {
        return websocketRepository.subscribe()
    }

    override suspend fun sendMessage(message: Message) {
        websocketRepository.sendMessage(message)
    }

    override suspend fun unsubscribe() {
        websocketRepository.unsubscribe()
    }
}