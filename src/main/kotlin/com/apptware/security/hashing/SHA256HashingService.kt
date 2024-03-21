package com.apptware.security.hashing

interface SHA256HashingService {
    fun generateSaltedHash(value: String, saltLength: Int = 32): SaltedHash

    fun verifyHash(value: String, saltedHash: SaltedHash): Boolean
}