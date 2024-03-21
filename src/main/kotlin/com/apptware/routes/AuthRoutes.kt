package com.apptware.routes

import com.apptware.data.request.AuthRequest
import com.apptware.data.request.LoginRequest
import com.apptware.data.responses.ApiResponse
import com.apptware.data.responses.AuthResponse
import com.apptware.util.CustomExceptions
import com.apptware.data.user.User
import com.apptware.data.user.UserDataSource
import com.apptware.security.token.JWTTokenService
import com.apptware.security.token.TokenClaim
import com.apptware.security.token.TokenConfig
import com.apptware.util.FAILURE
import com.apptware.util.SUCCESS
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signup(
    userDataSource: UserDataSource
) {
    post("signup") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ApiResponse(
                    data = "Wrong Input Data",
                    httpCode = HttpStatusCode.BadRequest.value,
                    message = FAILURE
                )
            )
            return@post
        }

        validateAuthRequest(request)

        val userExists = userDataSource.userExists(request.email.orEmpty())

        if (userExists) {
            call.respond(
                status = HttpStatusCode.Forbidden,
                message = ApiResponse(
                    data = "User with email ${request.email} already exists",
                    httpCode = HttpStatusCode.Forbidden.value,
                    message = FAILURE
                )
            )
        } else {
            val user = User(
                email = request.email.orEmpty(),
                firstName = request.firstName.orEmpty(),
                lastName = request.lastName.orEmpty(),
                designation = request.designation.orEmpty(),
                department = request.department.orEmpty()
            )

            val wasAcknowledged = userDataSource.insertNewUser(user)

            if (!wasAcknowledged) {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    message = ApiResponse(
                        data = "Something went wrong",
                        httpCode = HttpStatusCode.Conflict.value,
                        message = FAILURE
                    )
                )
                return@post
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = ApiResponse(
                    data = "User signed up successfully",
                    httpCode = HttpStatusCode.OK.value,
                    message = SUCCESS
                )
            )
        }

    }
}

fun Route.signIn(
    userDataSource: UserDataSource,
    tokensService: JWTTokenService,
    tokenConfig: TokenConfig
) {
    post("signin") {
        val request = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ApiResponse(
                    data = "Wrong Input Data",
                    httpCode = HttpStatusCode.BadRequest.value,
                    message = FAILURE
                )
            )
            return@post
        }
        validateLoginRequest(loginRequest = request)
        val user = userDataSource.getUserByEmail(request.email.orEmpty())

        if (user == null) {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ApiResponse(
                    data = "User doesn't exist",
                    httpCode = HttpStatusCode.Conflict.value,
                    message = FAILURE
                )
            )
            return@post
        }

        val token = tokensService.generate(
            tokenConfig,
            TokenClaim(name = "userId", value = user.id.toString())
        )

        call.respond(
            status = HttpStatusCode.OK,
            message = ApiResponse(
                message = SUCCESS,
                data = AuthResponse(
                    token = token,
                    email = user.email,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    department = user.department,
                    designation = user.designation
                ),
                httpCode = HttpStatusCode.OK.value
            )
        )
    }
}

fun Route.authenticate() {
    authenticate {
        get("authenticate") {
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun validateAuthRequest(authRequest: AuthRequest) {
    if (authRequest.email.isNullOrEmpty()) {
        throw CustomExceptions.MissingFieldException("email")
    }
    if (authRequest.firstName.isNullOrEmpty()) {
        throw CustomExceptions.MissingFieldException("firstName")
    }
}

fun validateLoginRequest(loginRequest: LoginRequest) {
    if (loginRequest.email.isNullOrEmpty()) {
        throw CustomExceptions.MissingFieldException("email")
    }
}