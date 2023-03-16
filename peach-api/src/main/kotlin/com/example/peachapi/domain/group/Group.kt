package com.example.peachapi.domain.group

import com.example.peachapi.domain.FCC
import com.example.peachapi.domain.GroupInviteCode
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.domain.user.UserName
import java.util.UUID

data class Groups(override val list: List<Group>): FCC<Group> {
    fun isExist(groupId: GroupId): Boolean =
        this.find { it.groupId.value === groupId.value } !== null
}

data class Group(
    val groupId: GroupId,
    val groupName: GroupName,
    val groupRemarks: GroupRemarks?,
    val createBy: UserId,
    val changedBy: UserId,
    val groupInviteCode: GroupInviteCode?
) {
    companion object {
        fun newGroup(name: String, remarks: String?, createAt: UserId): Group =
            Group(
                GroupId(UUID.randomUUID()),
                GroupName(name),
                if(remarks == null) null else GroupRemarks(remarks),
                createAt,
                createAt,
                null
            )
    }
}

data class UserGroups(override val list: List<UserGroup>): FCC<UserGroup>

data class UserGroup(
    val groupId: GroupId,
    val userId: UserId,
    val userName: UserName?
)
data class GroupId(val value: UUID)
data class GroupName(val value: String)
data class GroupRemarks(val value: String)