package com.android.ktorsample.feature.websocket.di

import com.android.ktorsample.core.data.network.WebsocketNetworkClient
import com.android.ktorsample.feature.websocket.data.WebsocketRepositoryImpl
import com.android.ktorsample.feature.websocket.data.network.WebsocketKtorNetworkClientImpl
import com.android.ktorsample.feature.websocket.data.network.dto.MessageDto
import com.android.ktorsample.feature.websocket.domain.api.WebsocketInteractor
import com.android.ktorsample.feature.websocket.domain.api.WebsocketRepository
import com.android.ktorsample.feature.websocket.domain.impl.WebsocketInteractorImpl
import com.android.ktorsample.feature.websocket.presentation.WebsocketViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val websocketModule = module {
    single<WebsocketNetworkClient<MessageDto, MessageDto>> {
        WebsocketKtorNetworkClientImpl()
    }

    single<WebsocketRepository> {
        WebsocketRepositoryImpl(
            websocketNetworkClient = get()
        )
    }

    factory<WebsocketInteractor> {
        WebsocketInteractorImpl(
            websocketRepository = get()
        )
    }

    viewModel {
        WebsocketViewModel(
            websocketInteractor = get()
        )
    }
}