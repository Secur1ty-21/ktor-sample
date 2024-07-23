package com.android.ktorsample.feature.websocket.data

import android.util.Log
import com.android.ktorsample.BuildConfig
import com.android.ktorsample.core.data.network.WebsocketNetworkClient
import com.android.ktorsample.feature.websocket.data.network.dto.MessageDto
import com.android.ktorsample.feature.websocket.data.network.dto.mapToData
import com.android.ktorsample.feature.websocket.data.network.dto.mapToDomain
import com.android.ktorsample.feature.websocket.domain.api.WebsocketRepository
import com.android.ktorsample.feature.websocket.domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WebsocketRepositoryImpl(
    private val websocketNetworkClient: WebsocketNetworkClient<MessageDto, MessageDto>
): WebsocketRepository {
    override fun subscribe(): Flow<Message> {
        return websocketNetworkClient.subscribe().map { it.mapToDomain() }
    }

    override suspend fun sendMessage(message: Message) {
        runCatching {
            websocketNetworkClient.sendMessage(message = message.mapToData())
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                Log.v(TAG, "error -> ${error.localizedMessage}")
                error.printStackTrace()
            }
        }
    }

    override suspend fun unsubscribe() {
        runCatching {
            websocketNetworkClient.close()
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                Log.v(TAG, "error -> ${error.localizedMessage}")
                error.printStackTrace()
            }
        }
    }

    private companion object {
        val TAG = WebsocketRepository::class.simpleName ?: "WebsocketRepository"
    }
}