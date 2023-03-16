package com.example.peachapi.domain

import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.user.UserId

data class GroupInviteCode (
    val groupId: GroupId,
    val inviteCode: InviteCode,
    val termTo: PeachDateTime,
    val inviteBy: UserId
) {
    fun isTermOfValidity() =
        PeachDateTime.now().value.let {
            this.termTo.value.isAfter(it) || this.termTo.value.isEqual(it)
        }
    companion object {
        fun createNewCode(groupId: GroupId,code: String, inviteBy: UserId) =
            GroupInviteCode(
                groupId,
                InviteCode(code),
                PeachDateTime.afterMinutes(30),
                inviteBy
            )
    }
}

data class InviteCode(
    val value: String
) {
    fun isSame(code: InviteCode) =
        this.value == code.value
}