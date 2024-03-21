package com.apptware

import com.apptware.plugins.*
import com.apptware.security.token.TokenConfig
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = "jwt-secret-token-for-hrms"
    )

    configureKoin()
//    configureHTTP()
    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenConfig)
    configureRouting(tokenConfig)
    configureStatusPage()
}
