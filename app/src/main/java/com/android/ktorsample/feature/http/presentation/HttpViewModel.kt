package com.android.ktorsample.feature.http.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ktorsample.BuildConfig
import com.android.ktorsample.core.domain.model.ErrorType
import com.android.ktorsample.core.domain.model.Result
import com.android.ktorsample.feature.http.domain.usecase.GetBreedListUseCase
import com.android.ktorsample.feature.http.domain.usecase.GetRandomCatFactUseCase
import com.android.ktorsample.feature.http.presentation.model.HttpCatsEvent
import com.android.ktorsample.feature.http.presentation.model.HttpCatsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HttpViewModel(
    private val getStatusCodeDetailsUseCase: GetRandomCatFactUseCase,
    private val getBreedListUseCase: GetBreedListUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<HttpCatsState?> = MutableStateFlow(null)
    val state: StateFlow<HttpCatsState?> get() = _state

    fun obtainEvent(event: HttpCatsEvent) {
        when (event) {
            is HttpCatsEvent.OnBtnGetRandomCatFactClick -> getRandomCatFact()

            is HttpCatsEvent.OnBtnGetBreeds -> getBreeds()
        }
    }

    private fun getRandomCatFact() {
        _state.update { HttpCatsState.Loading }
        runSafelyUseCase(
            useCaseFlow = getStatusCodeDetailsUseCase.execute(),
            onSuccess = { fact -> _state.update { HttpCatsState.CatFact(fact) } }
        )
    }

    private fun getBreeds() {
        _state.update { HttpCatsState.Loading }
        runSafelyUseCase(
            useCaseFlow = getBreedListUseCase.execute(),
            onSuccess = { breeds -> _state.update { HttpCatsState.BreedList(breeds) } }
        )
    }

    private inline fun <reified D> runSafelyUseCase(
        useCaseFlow: Flow<Result<D, ErrorType>>,
        noinline onFailure: ((ErrorType) -> Unit)? = null,
        crossinline onSuccess: (D) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                useCaseFlow.collect { result ->
                    when (result) {
                        is Result.Success -> onSuccess(result.data)
                        is Result.Failure -> {
                            onFailure?.invoke(result.error)
                                ?: _state.update { HttpCatsState.Error(result.error) }
                        }
                    }
                }
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    Log.v(TAG, "error -> ${error.localizedMessage}")
                    error.printStackTrace()
                }
                onFailure?.invoke(ErrorType.UNKNOWN_ERROR)
                    ?: _state.update { HttpCatsState.Error(ErrorType.UNKNOWN_ERROR) }
            }
        }
    }

    private companion object {
        val TAG = HttpViewModel::class.simpleName ?: "HttpViewModel"
    }
}