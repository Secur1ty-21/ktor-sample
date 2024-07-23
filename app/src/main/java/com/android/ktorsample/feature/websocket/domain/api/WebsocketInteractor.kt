package com.android.ktorsample.feature.websocket.domain.api

import com.android.ktorsample.feature.websocket.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface WebsocketInteractor {
    fun subscribe(): Flow<Message>
    suspend fun sendMessage(message: Message)
    suspend fun unsubscribe()
}