package com.android.ktorsample.core

import com.android.ktorsample.feature.http.di.httpModule
import com.android.ktorsample.feature.websocket.di.websocketModule

object DiProvider {
    val modules = listOf(
        httpModule,
        websocketModule
    )
}