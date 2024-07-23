package com.android.ktorsample.feature.http.data

import com.android.ktorsample.core.data.network.HttpNetworkClient
import com.android.ktorsample.core.data.network.model.mapToErrorType
import com.android.ktorsample.core.domain.model.ErrorType
import com.android.ktorsample.core.domain.model.Result
import com.android.ktorsample.feature.http.data.network.CatFactRequest
import com.android.ktorsample.feature.http.data.network.CatFactResponse
import com.android.ktorsample.feature.http.data.network.dto.mapToDomain
import com.android.ktorsample.feature.http.domain.api.CatRepository
import com.android.ktorsample.feature.http.domain.model.Breed
import com.android.ktorsample.feature.http.domain.model.RandomCatFact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CatRepositoryImpl(
    private val httpNetworkClient: HttpNetworkClient<CatFactRequest, CatFactResponse>
): CatRepository {
    override fun getRandomCatFact(): Flow<Result<RandomCatFact, ErrorType>> = flow {
        val response = httpNetworkClient.getResponse(CatFactRequest.RandomCatFact)
        when (val body = response.body) {
            is CatFactResponse.RandomCatFact -> {
                emit(Result.Success(body.value.mapToDomain()))
            }

            else -> {
                emit(Result.Failure(response.resultCode.mapToErrorType()))
            }
        }
    }

    override fun getBreedList(): Flow<Result<List<Breed>, ErrorType>> = flow {
        val response = httpNetworkClient.getResponse(CatFactRequest.BreedList())
        when (val body = response.body) {
            is CatFactResponse.BreedList -> {
                emit(Result.Success(body.value.map { it.mapToDomain() }))
            }

            else -> {
                emit(Result.Failure(response.resultCode.mapToErrorType()))
            }
        }
    }
}