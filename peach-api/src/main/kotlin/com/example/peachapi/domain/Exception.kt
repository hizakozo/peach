package com.example.peachapi.domain

//import com.example.peachapi.domain.user.UserRepositoryError

sealed class ApiException(open val message: String?)
data class PermissionException(val throwable: Throwable?, override val message: String?) : ApiException(message)
data class NotFoundDataException(val throwable: Throwable?, override val message: String?): ApiException(message)
data class UnExpectError(val cause: Throwable?, override val message: String?): ApiException(message)
data class VerifyIdTokenException(override val message: String = "failed verify idToken"): ApiException(message)
data class UnAuthorizeException(override val message: String?) : ApiException(message)