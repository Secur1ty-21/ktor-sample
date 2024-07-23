package com.android.ktorsample.core.data.network.model

import com.android.ktorsample.core.domain.model.ErrorType

@JvmInline
value class StatusCode(val code: Int)

fun StatusCode.mapToErrorType(): ErrorType {
    return when (code) {
        -1 -> ErrorType.NO_CONNECTION
        400 -> ErrorType.BAD_REQUEST
        404 -> ErrorType.NOT_FOUND
        500 -> ErrorType.SERVER_ERROR
        else -> ErrorType.UNKNOWN_ERROR
    }
}