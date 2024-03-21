package com.apptware.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val department: String,
    val designation: String,
)
