package com.example.peachapi.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.example.peachapi.domain.ApiException
import com.example.peachapi.domain.PermissionException
import com.example.peachapi.domain.category.CategoryId
import com.example.peachapi.domain.category.CategoryRepository
import com.example.peachapi.domain.group.GroupId
import com.example.peachapi.domain.group.GroupRepository
import com.example.peachapi.domain.item.Item
import com.example.peachapi.domain.item.ItemRepository
import com.example.peachapi.domain.item.Items
import com.example.peachapi.domain.user.UserId
import com.google.protobuf.Api
import org.springframework.stereotype.Component

@Component
class ItemUseCase(private val repository: ItemRepository, private val groupRepository: GroupRepository) {
    fun getByCategoryId(categoryId: CategoryId, userId: UserId, groupId: GroupId): Either<ApiException, Items> =
        groupRepository.existsUserGroup(userId, groupId)
            .flatMap {isExist ->
                if (isExist) {
                    repository.getByCategoryId(categoryId)
                } else {
                    Either.Left(PermissionException(null, "unavailable category"))
                }
            }

    fun createItem(userId: UserId, groupId: GroupId, item: Item): Either<ApiException, Item> =
        groupRepository.existsUserGroup(userId, groupId)
            .flatMap {
                if (it) {
                    repository.createItem(item)
                } else {
                    Either.Left(PermissionException(null, "unavailable category"))
                }
            }
}