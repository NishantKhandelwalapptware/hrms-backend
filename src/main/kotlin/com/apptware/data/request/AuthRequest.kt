package com.apptware.data.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val department: String?,
    val designation: String?,
)