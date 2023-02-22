package com.example.peachapi.driver.peachdb

import arrow.core.Either
import com.example.peachapi.domain.group.Group
import com.example.peachapi.domain.group.UserGroup
import com.example.peachapi.driver.peachdb.gen.Tables.GROUPS
import com.example.peachapi.driver.peachdb.gen.tables.GoogleAuthentications
import com.example.peachapi.driver.peachdb.gen.tables.Users
import org.jooq.DSLContext
import org.jooq.impl.DSL

class GroupDbDriver(private val dsl: DSLContext) {
    fun create(group: Group, userGroup: UserGroup, dsl: DSLContext): Either<Throwable, Unit> =
        Either.catch {
            dsl.transactionResult { config ->
                val context = DSL.using(config)
                context.insertInto(GROUPS)
                    .set(GROUPS.GROUP_ID, group.groupId.value)
            }
        }
}