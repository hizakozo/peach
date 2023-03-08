package com.example.peachapi.domain.item

import com.example.peachapi.domain.FCC
import com.example.peachapi.domain.PeachDateTime
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.status.Status
import com.example.peachapi.domain.status.StatusId
import com.example.peachapi.domain.user.UserId
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

data class Items(override val list: List<Item>): FCC<Item>
data class Item(
    val itemId:ItemId,
    val categoryId: CategoryId,
    val statusId: StatusId?,
    val itemName: ItemName,
    val itemRemarks: ItemRemarks?,
    val createBy: UserId,
    val changedBy: UserId,
    val createdAt: PeachDateTime
) {
    companion object {
        fun newItem(categoryId: CategoryId, itemName: String, itemRemarks: String, userId: UserId) =
            Item(
                ItemId(UUID.randomUUID()),
                categoryId,
                null,
                ItemName(itemName),
                ItemRemarks(itemRemarks),
                userId, userId, PeachDateTime.now()
            )
    }
}
data class ItemId(val value: UUID)
data class ItemName(val value: String)
data class ItemRemarks(val value: String)
