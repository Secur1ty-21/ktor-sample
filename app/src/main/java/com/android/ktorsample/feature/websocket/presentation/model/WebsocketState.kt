package com.android.ktorsample.feature.websocket.presentation.model

import com.android.ktorsample.core.domain.model.ErrorType

sealed interface WebsocketState {
    data object Default : WebsocketState
    data object Loading : WebsocketState
    class Message(val msg: String) : WebsocketState
    class Error(val error: ErrorType) : WebsocketState
}