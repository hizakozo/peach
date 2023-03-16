package com.example.peachapi.driver.peachdb.fireBase

import arrow.core.Either
import com.example.peachapi.config.CustomConfig
import com.example.peachapi.domain.user.UserId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.google.firebase.auth.UserRecord
import org.springframework.stereotype.Component
@Component
class FirebaseAuthDriver(private val firebaseAuth: FirebaseAuth) {
    fun getUserRecordByUID(uid: UserId): Either<Throwable, UserRecord> =
        Either.catch {
            firebaseAuth.getUser(uid.value)
        }
}