package com.example.peachapi.domain.user

import com.example.peachapi.domain.valueObjects.ChangedAt
import com.example.peachapi.domain.valueObjects.CreatedAd
import com.example.peachapi.domain.valueObjects.DeletedAt
import java.time.LocalDate
import java.util.UUID

data class User(
    val userId: UserId,
    val userName: UserName,
    val mailAddress: MailAddress,
)

data class Auth(
    val principalId: PrincipalId,
)

data class UserId(val value: String)
data class UserName(val value: String)
data class BirthDay(val value: LocalDate?)
data class MailAddress(val value: String)
data class PrincipalId(val value: String)