package com.example.peachapi.domain.status

import com.example.peachapi.domain.FCC
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.item.Item
import com.example.peachapi.domain.user.UserId
import java.util.UUID
data class Statuses(override val list: List<Status>): FCC<Status>
data class Status(
    val statusId: StatusId,
    val categoryId: CategoryId,
    val statusName: StatusName,
    val statusColor: StatusColor,
    val createBy: UserId,
    val changedBy: UserId
) {
    companion object {
        fun newStatus(categoryId: CategoryId, name: String, color: String, createBy: UserId) =
            Status(
                StatusId(UUID.randomUUID()),
                categoryId,
                StatusName(name),
                StatusColor(color),
                createBy,
                createBy
            )
    }
}

data class StatusId(val value: UUID)
data class StatusName(val value: String)
data class StatusColor(val value: String)