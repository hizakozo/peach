package com.example.peachapi.domain

//import com.example.peachapi.domain.user.UserRepositoryError

interface ApiException
data class PermissionException(val throwable: Throwable?) : ApiException
data class UnExpectError(val cause: Throwable?, val message: String = "un expect error"): ApiException
data class VerifyIdTokenException(val message: String = "failed verify idToken"): ApiException
data class UnAuthorizeException(val message: String?) : ApiException