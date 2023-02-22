package com.example.peachapi.domain.category

import com.example.peachapi.domain.FCC
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.group.GroupName
import com.example.peachapi.domain.user.UserId
import java.util.*

data class Categories(override val list: List<Category>): FCC<Category>

data class Category (
    val categoryId: CategoryId,
    val groupId: GroupId,
    val categoryName: CategoryName,
    val categoryRemarks: CategoryRemarks,
    val createBy: UserId,
    val changedBy: UserId
) {
    companion object {
        fun newCategory(name: String, groupId: GroupId, remarks: String, createBy: UserId) =
            Category(
                CategoryId(UUID.randomUUID()),
                groupId,
                CategoryName(name),
                CategoryRemarks(remarks),
                createBy,
                createBy
            )
        fun of(categoryId: UUID, groupId: UUID, categoryName: String,
               remarks: String, createBy: UserId,
               changedBy: UserId) =
            Category(
                CategoryId(categoryId), GroupId(groupId), CategoryName(categoryName),
                CategoryRemarks(remarks), createBy, changedBy
            )
    }
}

data class CategoryId(val value: UUID)
data class CategoryName(val value: String)
data class CategoryRemarks(val value: String)