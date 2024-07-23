package com.android.ktorsample.feature.http.presentation.model

import com.android.ktorsample.core.domain.model.ErrorType
import com.android.ktorsample.feature.http.domain.model.Breed
import com.android.ktorsample.feature.http.domain.model.RandomCatFact

sealed interface HttpCatsState {
    data object Loading: HttpCatsState
    class Error(val errorType: ErrorType) : HttpCatsState
    class CatFact(val fact: RandomCatFact) : HttpCatsState
    class BreedList(val breedList: List<Breed>) : HttpCatsState
}