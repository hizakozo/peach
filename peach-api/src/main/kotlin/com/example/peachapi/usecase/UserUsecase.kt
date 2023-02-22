package com.example.peachapi.usecase

import arrow.core.Either
import arrow.core.flatMap
import arrow.fx.coroutines.parZip
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.peachapi.config.CustomConfig
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.UnAuthorizeException
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.VerifyIdTokenException
import com.example.peachapi.domain.user.PrincipalId
import com.example.peachapi.domain.user.User
import com.example.peachapi.domain.user.UserRepository
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserUseCase(private val userRepository: UserRepository, private val config: CustomConfig) {
    suspend fun login(idToken: String): Either<ApiException, String> =
        getPayLoad(idToken)
            .mapLeft { VerifyIdTokenException() }
            .flatMap { payload ->
                userRepository.getUser(PrincipalId(payload.principalId)).mapLeft { UnExpectError(null, null) }
                    .flatMap { user ->
                        if(user === null) {
                            val newUser = User.newUser(payload.name, payload.email)
                            userRepository.createUser(newUser, PrincipalId(payload.principalId))
                            createJwt(newUser)
                        }
                        else createJwt(user)
                    }
            }
    private suspend fun getPayLoad(idToken: String): Either<FailedVerifyError, GoogleAuthPayLoad> =
        parZip(
            {NetHttpTransport()},
            {GsonFactory.getDefaultInstance()}
        ) {transport, jsonFactory ->
            GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(config.googleClientId))
                .build()
                .verify(idToken)
                .let {
                    if (it === null) Either.Left(FailedVerifyError(null))
                    else Either.Right(GoogleAuthPayLoad.fromJsonPayLoad(it.payload))
                }
        }

    private fun createJwt(user: User): Either<UnAuthorizeException, String> =
        Either.catch {
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            calendar.add(Calendar.YEAR, 1)
            JWT.create()
                .withIssuer("auth0")
                .withClaim("userName", user.userName.value)
                .withClaim("userId", user.userId.value.toString())
                .withClaim("mailAddress", user.mailAddress.value)
                .withExpiresAt(calendar.time)
                .sign(Algorithm.HMAC256(config.jwtSecretKey))
        }.mapLeft {
            UnAuthorizeException("jwt err: " + it.message)
        }
}

data class FailedVerifyError(val throwable: Throwable?)
data class GoogleAuthPayLoad(
    val name: String,
    val principalId: String,
    val email: String
) {
    companion object {
        fun fromJsonPayLoad(payload: GoogleIdToken.Payload): GoogleAuthPayLoad =
            GoogleAuthPayLoad(
                payload["name"].toString(),
                payload["sub"].toString(),
                payload["email"].toString()
            )
    }
}