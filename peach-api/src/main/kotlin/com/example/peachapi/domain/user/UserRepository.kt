package com.example.peachapi.domain.user

import arrow.core.Either
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.UnExpectError

interface UserRepository {
    fun createUser(user: User, principalId: PrincipalId): Either<ApiException, User>
    fun getUser(principalId: PrincipalId): Either<ApiException, User?>
}
