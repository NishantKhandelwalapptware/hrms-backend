package com.apptware.plugins

import com.apptware.data.user.UserDataSource
import com.apptware.routes.authenticate
import com.apptware.routes.signIn
import com.apptware.routes.signup
import com.apptware.security.token.JWTTokenService
import com.apptware.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting(
    tokenConfig: TokenConfig
) {
    val userDataSource by inject<UserDataSource>()
    val tokenService by inject<JWTTokenService>()
    routing {
        signup(userDataSource)
        signIn(userDataSource, tokenService, tokenConfig)
        authenticate()
    }
}
