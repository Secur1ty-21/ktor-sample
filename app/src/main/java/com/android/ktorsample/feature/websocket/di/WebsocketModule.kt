package com.android.ktorsample.feature.websocket.di

import com.android.ktorsample.feature.websocket.presentation.WebsocketViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val websocketModule = module {
    viewModel {
        WebsocketViewModel()
    }
}