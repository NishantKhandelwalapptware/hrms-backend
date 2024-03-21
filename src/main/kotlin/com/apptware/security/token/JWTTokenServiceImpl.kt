package com.apptware.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

class JWTTokenServiceImpl : JWTTokenService {
    override fun generate(config: TokenConfig, vararg claims: TokenClaim): String {
        var token = JWT.create().withAudience(config.audience).withIssuer(config.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + config.expiresIn))

        claims.forEach { tokenClaim ->
            token = token.withClaim(tokenClaim.name, tokenClaim.value)
        }

        return token.sign(Algorithm.HMAC256(config.secret))
    }
}