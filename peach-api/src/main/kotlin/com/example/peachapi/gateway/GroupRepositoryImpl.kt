package com.example.peachapi.gateway

import arrow.core.Either
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.group.*
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.GroupDbDriver
import com.example.peachapi.driver.peachdb.GroupRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Component

@Component
class GroupRepositoryImpl(private val dbDriver: GroupDbDriver, private val dsl: DSLContext): GroupRepository {
    override fun create(group: Group): Either<ApiException, Unit> =
        dbDriver.create(group, dsl)
            .mapLeft { UnExpectError(it, it.message) }

    override fun getGroups(userId: UserId): Either<ApiException, Groups> =
        dbDriver.getGroups(userId)
            .mapLeft { UnExpectError(it, it.message) }
            .map { it.toGroups() }
}

fun List<GroupRecord>.toGroups(): Groups =
    Groups(
        this.map { it.toGroup() }
    )

fun GroupRecord.toGroup(): Group =
    Group(
        GroupId(this.groupId),
        GroupName(this.groupName),
        GroupRemarks(this.groupRemarks),
        UserId(this.createdBy),
        UserId(this.changedBy)
    )