package com.android.ktorsample.feature.websocket.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ktorsample.BuildConfig
import com.android.ktorsample.core.domain.model.ErrorType
import com.android.ktorsample.feature.websocket.domain.api.WebsocketInteractor
import com.android.ktorsample.feature.websocket.domain.model.Message
import com.android.ktorsample.feature.websocket.presentation.model.WebsocketEvent
import com.android.ktorsample.feature.websocket.presentation.model.WebsocketState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

class WebsocketViewModel(
    private val websocketInteractor: WebsocketInteractor
): ViewModel() {
    val state: StateFlow<WebsocketState> = websocketInteractor
        .subscribe()
        .onStart { WebsocketState.Loading }
        .catch { error ->
            if (BuildConfig.DEBUG) {
                Log.v(TAG, "error -> ${error.localizedMessage}")
                error.printStackTrace()
            }
            WebsocketState.Error(ErrorType.UNKNOWN_ERROR)
        }
        .map { WebsocketState.Message(it.text) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WebsocketState.Default)

    fun obtainEvent(event: WebsocketEvent) {
        when (event) {
            is WebsocketEvent.OnBtnSendMessageClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    sendMessage(event.message)
                }
            }

            is WebsocketEvent.OnDestroyView -> {
                viewModelScope.launch(Dispatchers.IO) {
                    websocketInteractor.unsubscribe()
                }
            }
        }
    }

    private suspend fun sendMessage(text: String) {
        runCatching {
            websocketInteractor.sendMessage(Message(text))
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                Log.v(TAG, "error -> ${error.localizedMessage}")
                error.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        CoroutineScope(EmptyCoroutineContext).launch(Dispatchers.IO) {
            websocketInteractor.unsubscribe()
        }
        super.onCleared()
    }

    private companion object {
        val TAG = WebsocketViewModel::class.simpleName ?: "WebsocketViewModel"
    }
}