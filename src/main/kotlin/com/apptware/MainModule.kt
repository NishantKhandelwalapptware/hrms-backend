package com.apptware

import com.apptware.data.user.UserDataSource
import com.apptware.data.user.UserDataSourceImpl
import com.apptware.security.hashing.SHA256HashingService
import com.apptware.security.hashing.SHA256HashingServiceImpl
import com.apptware.security.token.JWTTokenService
import com.apptware.security.token.JWTTokenServiceImpl
import com.apptware.security.token.TokenConfig
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        KMongo.createClient().coroutine.getDatabase("hrms")
    }
    single<UserDataSource> {
        UserDataSourceImpl(get())
    }
    single<JWTTokenService> {
        JWTTokenServiceImpl()
    }
    single<SHA256HashingService> {
        SHA256HashingServiceImpl()
    }
}