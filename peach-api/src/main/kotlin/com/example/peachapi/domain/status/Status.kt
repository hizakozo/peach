package com.example.peachapi.domain.status

import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.user.UserId
import java.util.UUID

data class Status(
    val statusId: StatusId,
    val categoryId: CategoryId,
    val statusName: StatusName,
    val statusColor: StatusColor,
    val createBy: UserId,
    val changedBy: UserId
)

data class StatusId(val value: UUID)
data class StatusName(val value: String)
data class StatusColor(val value: String)