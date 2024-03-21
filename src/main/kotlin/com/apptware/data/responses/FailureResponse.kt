package com.apptware.data.responses

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class FailureResponse(
    val httpCode: Int,
    val message: String
)
