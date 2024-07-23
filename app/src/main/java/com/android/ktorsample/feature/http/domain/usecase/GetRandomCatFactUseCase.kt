package com.android.ktorsample.feature.http.domain.usecase

import com.android.ktorsample.core.domain.model.Result
import com.android.ktorsample.core.domain.model.ErrorType
import com.android.ktorsample.feature.http.domain.api.CatRepository
import com.android.ktorsample.feature.http.domain.model.RandomCatFact
import kotlinx.coroutines.flow.Flow

class GetRandomCatFactUseCase(
    private val catRepository: CatRepository
) {
    fun execute(): Flow<Result<RandomCatFact, ErrorType>> {
        return catRepository.getRandomCatFact()
    }
}