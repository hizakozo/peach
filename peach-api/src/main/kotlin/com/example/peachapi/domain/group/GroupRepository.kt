package com.example.peachapi.domain.group

import arrow.core.Either
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.GroupInviteCode
import com.example.peachapi.domain.InviteCode
import com.example.peachapi.domain.UnExpectError
import com.example.peachapi.domain.user.User
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.domain.user.UserName

interface GroupRepository {
    fun create(group: Group): Either<UnExpectError, Unit>
    fun getGroups(userId: UserId): Either<UnExpectError, Groups>
    suspend fun getGroup(groupId: GroupId): Either<UnExpectError, Pair<Group?, UserGroups>>
    fun existsUserGroup(userId: UserId, groupId: GroupId): Either<UnExpectError, Boolean>
    suspend fun update(groupId: GroupId, groupName: GroupName, groupRemarks: GroupRemarks, changedBy: UserId): Either<UnExpectError, Group>
    suspend fun delete(groupId: GroupId, userId: UserId): Either<UnExpectError, GroupId>
    suspend fun existsInviteCode(inviteCode: InviteCode): Either<UnExpectError, Boolean>
    suspend fun createInviteCode(groupInviteCode: GroupInviteCode, userId: UserId): Either<UnExpectError, Pair<GroupInviteCode, UserName>>
    suspend fun fetchGroupInviteCode(groupId: GroupId): Either<UnExpectError, Pair<GroupInviteCode, UserName>?>
    suspend fun createUserGroups(userId: UserId, groupId: GroupId): Either<UnExpectError, Pair<UserId, GroupId>>
    suspend fun deleteUserGroups(userId: UserId, groupId: GroupId): Either<UnExpectError, GroupId>
}
