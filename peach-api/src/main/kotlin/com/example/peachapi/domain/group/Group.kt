package com.example.peachapi.domain.group

import com.example.peachapi.domain.FCC
import com.example.peachapi.domain.user.UserId
import java.util.UUID

data class Groups(override val list: List<Group>): FCC<Group>

data class Group(
    val groupId: GroupId,
    val groupName: GroupName,
    val groupRemarks: GroupRemarks,
    val createBy: UserId,
    val changedBy: UserId
) {
    companion object {
        fun newGroup(name: String, remarks: String, createAt: UserId): Group =
            Group(
                GroupId(UUID.randomUUID()),
                GroupName(name),
                GroupRemarks(remarks),
                createAt,
                createAt
            )
    }
}

data class UserGroups(override val list: List<UserGroup>): FCC<UserGroup>

data class UserGroup(
    val groupId: GroupId,
    val userId: UserId
)
data class GroupId(val value: UUID)
data class GroupName(val value: String)
data class GroupRemarks(val value: String)