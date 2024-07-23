package com.android.ktorsample.feature.http.presentation.model

sealed interface HttpCatsEvent {
    data object OnBtnGetRandomCatFactClick : HttpCatsEvent
    data object OnBtnGetBreeds : HttpCatsEvent
}