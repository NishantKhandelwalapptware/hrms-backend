package com.apptware.data.user

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserDataSourceImpl(
    db: CoroutineDatabase
) : UserDataSource {
    private val users = db.getCollection<User>()
    override suspend fun getUserByEmail(email: String): User? {
        return users.findOne(User::email eq email)
    }

    override suspend fun insertNewUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }

    override suspend fun userExists(email: String): Boolean {
        return users.findOne(User::email eq email) != null
    }
}