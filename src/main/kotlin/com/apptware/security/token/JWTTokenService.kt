package com.apptware.security.token

interface JWTTokenService {
    fun generate(
        config: TokenConfig,
        vararg claims: TokenClaim
    ): String
}