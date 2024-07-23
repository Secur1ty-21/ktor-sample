package com.android.ktorsample.core.data.network

import com.android.ktorsample.core.data.network.model.Response

interface HttpNetworkClient<T, R> {
    suspend fun getResponse(sealedRequest: T): Response<R>
}