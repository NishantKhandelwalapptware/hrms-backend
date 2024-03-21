package com.apptware.data.responses

data class ApiResponse<T>(
    var httpCode: Int? = null,
    var message: String? = null,
    var data: T? = null
)