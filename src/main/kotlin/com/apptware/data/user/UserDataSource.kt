package com.apptware.data.user

interface UserDataSource {
    suspend fun getUserByEmail(email: String): User?

    suspend fun insertNewUser(user: User): Boolean

    suspend fun userExists(email: String): Boolean
}