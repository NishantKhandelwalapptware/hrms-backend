package com.apptware.data.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String?
)
