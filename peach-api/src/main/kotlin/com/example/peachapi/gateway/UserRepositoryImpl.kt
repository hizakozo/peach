package com.example.peachapi.gateway

import arrow.core.Either
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.user.*
import com.example.peachapi.driver.peachdb.UserDbDriver
import com.example.peachapi.driver.peachdb.UserInfoResult
import org.jooq.DSLContext
import org.springframework.stereotype.Component

@Component
class UserRepositoryImpl(private val dbDriver: UserDbDriver, private val dsl: DSLContext) : UserRepository {
    override fun createUser(user: User, principalId: PrincipalId): Either<ApiException, User> =
        dbDriver.insertUser(user, principalId, dsl)
            .mapLeft { UnExpectError(it) }
            .map { user }

    override fun getUser(principalId: PrincipalId): Either<ApiException, User?> =
        dbDriver.findUserByPrincipalId(principalId)
            .mapLeft {
                UnExpectError(it) 
            }
            .map { it?.toUser() }

    private fun UserInfoResult.toUser() =
        User(
            UserId(this.userId),
            UserName(this.userName),
            BirthDay(this.birthDay),
            MailAddress(this.mailAddress),
        )
}