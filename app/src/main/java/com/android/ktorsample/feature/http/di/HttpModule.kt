package com.android.ktorsample.feature.http.di

import com.android.ktorsample.core.data.network.HttpNetworkClient
import com.android.ktorsample.feature.http.data.CatRepositoryImpl
import com.android.ktorsample.feature.http.data.network.CatFactRequest
import com.android.ktorsample.feature.http.data.network.CatFactResponse
import com.android.ktorsample.feature.http.data.network.HttpCatsKtorNetworkClient
import com.android.ktorsample.feature.http.domain.api.CatRepository
import com.android.ktorsample.feature.http.domain.usecase.GetBreedListUseCase
import com.android.ktorsample.feature.http.domain.usecase.GetRandomCatFactUseCase
import com.android.ktorsample.feature.http.presentation.HttpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val httpModule = module {
    single<HttpNetworkClient<CatFactRequest, CatFactResponse>> {
        HttpCatsKtorNetworkClient()
    }

    single<CatRepository> {
        CatRepositoryImpl(
            httpNetworkClient = get()
        )
    }

    factory {
        GetRandomCatFactUseCase(
            catRepository = get()
        )
    }

    factory {
        GetBreedListUseCase(
            catRepository = get()
        )
    }

    viewModel {
        HttpViewModel(
            getStatusCodeDetailsUseCase = get(),
            getBreedListUseCase = get()
        )
    }
}