package com.android.ktorsample.feature.http.domain.api

import com.android.ktorsample.core.domain.model.ErrorType
import com.android.ktorsample.core.domain.model.Result
import com.android.ktorsample.feature.http.domain.model.Breed
import com.android.ktorsample.feature.http.domain.model.RandomCatFact
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getRandomCatFact(): Flow<Result<RandomCatFact, ErrorType>>
    fun getBreedList(): Flow<Result<List<Breed>, ErrorType>>
}