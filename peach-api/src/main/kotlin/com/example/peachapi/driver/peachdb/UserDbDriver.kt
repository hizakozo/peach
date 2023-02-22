package com.example.peachapi.driver.peachdb

import arrow.core.Either
import com.example.peachapi.domain.user.Auth
import com.example.peachapi.domain.user.MailAddress
import com.example.peachapi.domain.user.PrincipalId
import com.example.peachapi.domain.user.User
import com.example.peachapi.driver.peachdb.gen.tables.GoogleAuthentications.GOOGLE_AUTHENTICATIONS
import com.example.peachapi.driver.peachdb.gen.tables.Users.USERS
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.UUID

@Component
class UserDbDriver(private val dsl: DSLContext) {
    fun findUserByPrincipalId(principalId: PrincipalId): Either<Throwable, UserInfoResult?> = Either.catch {
        dsl.select(USERS.USER_ID, USERS.USER_NAME, USERS.BIRTH_DAY, GOOGLE_AUTHENTICATIONS.MAIL_ADDRESS)
            .from(USERS)
            .join(GOOGLE_AUTHENTICATIONS)
            .on(USERS.USER_ID.eq(GOOGLE_AUTHENTICATIONS.USER_ID))
            .where(GOOGLE_AUTHENTICATIONS.PRINCIPAL_ID.eq(principalId.value))
            .fetchOne { record ->
                UserInfoResult.fromRecord(record)
            }
    }

    fun insertUser(user: User, principalId: PrincipalId, dsl: DSLContext): Either<Throwable, Unit> = Either.catch {
        dsl.transactionResult { config ->
            val context = DSL.using(config)
            context.insertInto(USERS)
                .set(USERS.USER_ID, user.userId.value)
                .set(USERS.USER_NAME, user.userName.value)
                .set(USERS.BIRTH_DAY, user.birthDay?.value)
                .execute()
            context.insertInto(GOOGLE_AUTHENTICATIONS)
                .set(GOOGLE_AUTHENTICATIONS.USER_ID, user.userId.value)
                .set(GOOGLE_AUTHENTICATIONS.PRINCIPAL_ID, principalId.value)
                .set(GOOGLE_AUTHENTICATIONS.MAIL_ADDRESS, user.mailAddress.value)
                .execute()
        }
    }
}

data class UserInfoResult (
    val userId: UUID,
    val userName: String,
    val birthDay: LocalDate?,
    val mailAddress: String
) {
    companion object {
        fun fromRecord(record: Record) =
            UserInfoResult(
                record.get(USERS.USER_ID),
                record.get(USERS.USER_NAME),
                record.get(USERS.BIRTH_DAY),
                record.get(GOOGLE_AUTHENTICATIONS.MAIL_ADDRESS)
            )
    }
}