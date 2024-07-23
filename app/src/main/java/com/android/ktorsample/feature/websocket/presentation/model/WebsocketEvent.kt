package com.android.ktorsample.feature.websocket.presentation.model

sealed interface WebsocketEvent {
    class OnBtnSendMessageClick(val message: String) : WebsocketEvent
    data object OnDestroyView : WebsocketEvent
}