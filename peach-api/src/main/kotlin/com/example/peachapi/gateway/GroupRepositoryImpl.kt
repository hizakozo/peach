package com.example.peachapi.gateway

import arrow.core.Either
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.group.*
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.driver.peachdb.GroupDbDriver
import com.example.peachapi.driver.peachdb.GroupRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Component

@Component
class GroupRepositoryImpl(private val dbDriver: GroupDbDriver, private val dsl: DSLContext): GroupRepository {
    override fun create(group: Group): Either<UnExpectError, Unit> =
        dbDriver.create(group, dsl)
            .toUnExpectError()

    override fun getGroups(userId: UserId): Either<UnExpectError, Groups> =
        dbDriver.getGroups(userId)
            .toUnExpectError()
            .map { it.toGroups() }

    override fun existsUserGroup(userId: UserId, groupId: GroupId): Either<UnExpectError, Boolean> =
        dbDriver.existUserGroup(userId, groupId)
            .toUnExpectError()

    override suspend fun update(groupId: GroupId, groupName: GroupName, groupRemarks: GroupRemarks, changedBy: UserId): Either<UnExpectError, Group> =
        dbDriver.update(groupId, groupName, groupRemarks, changedBy)
            .toUnExpectError()
            .map { it!!.toGroup() }
    override suspend fun delete(groupId: GroupId, userId: UserId): Either<UnExpectError, GroupId> =
        dbDriver.delete(groupId, userId)
            .toUnExpectError()
            .map { GroupId(it!!) }

    private fun List<GroupRecord>.toGroups(): Groups =
        Groups(
            this.map { it.toGroup() }
        )

    private fun GroupRecord.toGroup(): Group =
        Group(
            GroupId(this.groupId),
            GroupName(this.groupName),
            if (this.groupRemarks == null) null else GroupRemarks(this.groupRemarks),
            UserId(this.createdBy),
            UserId(this.changedBy)
        )
}