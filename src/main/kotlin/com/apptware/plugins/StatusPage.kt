package com.apptware.plugins

import com.apptware.data.responses.ApiResponse
import com.apptware.util.CustomExceptions
import com.apptware.util.FAILURE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import javax.naming.AuthenticationException

fun Application.configureStatusPage() {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            call.respond(
                message = ApiResponse(
                    httpCode = HttpStatusCode.BadRequest.value,
                    data = "404. Page not found",
                    message = FAILURE
                ),
                status = status
            )
        }

        exception<AuthenticationException> { call, exception ->
            call.respond(
                message = ApiResponse(
                    httpCode = HttpStatusCode.BadRequest.value,
                    data = "We caught an exception :${exception.message}",
                    message = FAILURE
                ),
                status = HttpStatusCode.Unauthorized
            )
        }

        exception<CustomExceptions.MissingFieldException> { call, exception ->
            call.respond(
                HttpStatusCode.BadRequest,
                ApiResponse(
                    httpCode = HttpStatusCode.BadRequest.value,
                    data = exception.message ?: "Missing required field",
                    message = FAILURE
                )
            )
        }
    }
}