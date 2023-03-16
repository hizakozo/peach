/*
 * This file is generated by jOOQ.
 */
package com.example.peachapi.driver.peachdb.gen;


import com.example.peachapi.driver.peachdb.gen.tables.AssignedStatus;
import com.example.peachapi.driver.peachdb.gen.tables.Categories;
import com.example.peachapi.driver.peachdb.gen.tables.Databasechangeloglock;
import com.example.peachapi.driver.peachdb.gen.tables.DeleteCategory;
import com.example.peachapi.driver.peachdb.gen.tables.DeleteGroup;
import com.example.peachapi.driver.peachdb.gen.tables.DeleteItem;
import com.example.peachapi.driver.peachdb.gen.tables.DeleteStatus;
import com.example.peachapi.driver.peachdb.gen.tables.GroupEntryQualifications;
import com.example.peachapi.driver.peachdb.gen.tables.Groups;
import com.example.peachapi.driver.peachdb.gen.tables.InviteGroup;
import com.example.peachapi.driver.peachdb.gen.tables.Items;
import com.example.peachapi.driver.peachdb.gen.tables.Statues;
import com.example.peachapi.driver.peachdb.gen.tables.UserGroups;
import com.example.peachapi.driver.peachdb.gen.tables.records.AssignedStatusRecord;
import com.example.peachapi.driver.peachdb.gen.tables.records.CategoriesRecord;
import com.example.peachapi.driver.peachdb.gen.tables.records.DatabasechangeloglockRecord;
import com.example.peachapi.driver.peachdb.gen.tables.records.DeleteCategoryRecord;
import com.example.peachapi.driver.peachdb.gen.tables.records.DeleteGroupRecord;
import com.example.peachapi.driver.peachdb.gen.tables.records.DeleteItemRecord;
import com.example.peachapi.driver.peachdb.gen.tables.records.DeleteStatusRecord;
import com.example.peachapi.driver.peachdb.gen.tables.records.GroupEntryQualificationsRecord;
import com.example.peachapi.driver.peachdb.gen.tables.records.GroupsRecord;
import com.example.peachapi.driver.peachdb.gen.tables.records.InviteGroupRecord;
import com.example.peachapi.driver.peachdb.gen.tables.records.ItemsRecord;
import com.example.peachapi.driver.peachdb.gen.tables.records.StatuesRecord;
import com.example.peachapi.driver.peachdb.gen.tables.records.UserGroupsRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AssignedStatusRecord> ASSIGNED_STATUS_PKEY = Internal.createUniqueKey(AssignedStatus.ASSIGNED_STATUS, DSL.name("assigned_status_pkey"), new TableField[] { AssignedStatus.ASSIGNED_STATUS.ITEM_ID }, true);
    public static final UniqueKey<CategoriesRecord> CATEGORIES_PKEY = Internal.createUniqueKey(Categories.CATEGORIES, DSL.name("categories_pkey"), new TableField[] { Categories.CATEGORIES.CATEGORY_ID }, true);
    public static final UniqueKey<DatabasechangeloglockRecord> DATABASECHANGELOGLOCK_PKEY = Internal.createUniqueKey(Databasechangeloglock.DATABASECHANGELOGLOCK, DSL.name("databasechangeloglock_pkey"), new TableField[] { Databasechangeloglock.DATABASECHANGELOGLOCK.ID }, true);
    public static final UniqueKey<DeleteCategoryRecord> DELETE_CATEGORY_PKEY = Internal.createUniqueKey(DeleteCategory.DELETE_CATEGORY, DSL.name("delete_category_pkey"), new TableField[] { DeleteCategory.DELETE_CATEGORY.CATEGORY_ID }, true);
    public static final UniqueKey<DeleteGroupRecord> DELETE_GROUP_PKEY = Internal.createUniqueKey(DeleteGroup.DELETE_GROUP, DSL.name("delete_group_pkey"), new TableField[] { DeleteGroup.DELETE_GROUP.GROUP_ID }, true);
    public static final UniqueKey<DeleteItemRecord> DELETE_ITEM_PKEY = Internal.createUniqueKey(DeleteItem.DELETE_ITEM, DSL.name("delete_item_pkey"), new TableField[] { DeleteItem.DELETE_ITEM.ITEM_ID }, true);
    public static final UniqueKey<DeleteStatusRecord> DELETE_STATUS_PKEY = Internal.createUniqueKey(DeleteStatus.DELETE_STATUS, DSL.name("delete_status_pkey"), new TableField[] { DeleteStatus.DELETE_STATUS.STATUS_ID }, true);
    public static final UniqueKey<GroupEntryQualificationsRecord> GROUP_ENTRY_QUALIFICATIONS_PKEY = Internal.createUniqueKey(GroupEntryQualifications.GROUP_ENTRY_QUALIFICATIONS, DSL.name("group_entry_qualifications_pkey"), new TableField[] { GroupEntryQualifications.GROUP_ENTRY_QUALIFICATIONS.GROUP_ID }, true);
    public static final UniqueKey<GroupsRecord> GROUPS_PKEY = Internal.createUniqueKey(Groups.GROUPS, DSL.name("groups_pkey"), new TableField[] { Groups.GROUPS.GROUP_ID }, true);
    public static final UniqueKey<InviteGroupRecord> INVITE_GROUP_INVITE_CODE_KEY = Internal.createUniqueKey(InviteGroup.INVITE_GROUP, DSL.name("invite_group_invite_code_key"), new TableField[] { InviteGroup.INVITE_GROUP.INVITE_CODE }, true);
    public static final UniqueKey<InviteGroupRecord> INVITE_GROUP_PKEY = Internal.createUniqueKey(InviteGroup.INVITE_GROUP, DSL.name("invite_group_pkey"), new TableField[] { InviteGroup.INVITE_GROUP.GROUP_ID }, true);
    public static final UniqueKey<ItemsRecord> ITEMS_PKEY = Internal.createUniqueKey(Items.ITEMS, DSL.name("items_pkey"), new TableField[] { Items.ITEMS.ITEM_ID }, true);
    public static final UniqueKey<StatuesRecord> STATUES_PKEY = Internal.createUniqueKey(Statues.STATUES, DSL.name("statues_pkey"), new TableField[] { Statues.STATUES.STATUS_ID }, true);
    public static final UniqueKey<UserGroupsRecord> USER_GROUPS_PKEY = Internal.createUniqueKey(UserGroups.USER_GROUPS, DSL.name("user_groups_pkey"), new TableField[] { UserGroups.USER_GROUPS.GROUP_ID, UserGroups.USER_GROUPS.USER_ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AssignedStatusRecord, ItemsRecord> ASSIGNED_STATUS__ASSIGNED_STATUS_ITEM_ID_FKEY = Internal.createForeignKey(AssignedStatus.ASSIGNED_STATUS, DSL.name("assigned_status_item_id_fkey"), new TableField[] { AssignedStatus.ASSIGNED_STATUS.ITEM_ID }, Keys.ITEMS_PKEY, new TableField[] { Items.ITEMS.ITEM_ID }, true);
    public static final ForeignKey<AssignedStatusRecord, StatuesRecord> ASSIGNED_STATUS__ASSIGNED_STATUS_STATUS_ID_FKEY = Internal.createForeignKey(AssignedStatus.ASSIGNED_STATUS, DSL.name("assigned_status_status_id_fkey"), new TableField[] { AssignedStatus.ASSIGNED_STATUS.STATUS_ID }, Keys.STATUES_PKEY, new TableField[] { Statues.STATUES.STATUS_ID }, true);
    public static final ForeignKey<CategoriesRecord, GroupsRecord> CATEGORIES__CATEGORIES_GROUP_ID_FKEY = Internal.createForeignKey(Categories.CATEGORIES, DSL.name("categories_group_id_fkey"), new TableField[] { Categories.CATEGORIES.GROUP_ID }, Keys.GROUPS_PKEY, new TableField[] { Groups.GROUPS.GROUP_ID }, true);
    public static final ForeignKey<DeleteCategoryRecord, CategoriesRecord> DELETE_CATEGORY__DELETE_CATEGORY_CATEGORY_ID_FKEY = Internal.createForeignKey(DeleteCategory.DELETE_CATEGORY, DSL.name("delete_category_category_id_fkey"), new TableField[] { DeleteCategory.DELETE_CATEGORY.CATEGORY_ID }, Keys.CATEGORIES_PKEY, new TableField[] { Categories.CATEGORIES.CATEGORY_ID }, true);
    public static final ForeignKey<DeleteGroupRecord, GroupsRecord> DELETE_GROUP__DELETE_GROUP_GROUP_ID_FKEY = Internal.createForeignKey(DeleteGroup.DELETE_GROUP, DSL.name("delete_group_group_id_fkey"), new TableField[] { DeleteGroup.DELETE_GROUP.GROUP_ID }, Keys.GROUPS_PKEY, new TableField[] { Groups.GROUPS.GROUP_ID }, true);
    public static final ForeignKey<DeleteItemRecord, ItemsRecord> DELETE_ITEM__DELETE_ITEM_ITEM_ID_FKEY = Internal.createForeignKey(DeleteItem.DELETE_ITEM, DSL.name("delete_item_item_id_fkey"), new TableField[] { DeleteItem.DELETE_ITEM.ITEM_ID }, Keys.ITEMS_PKEY, new TableField[] { Items.ITEMS.ITEM_ID }, true);
    public static final ForeignKey<DeleteStatusRecord, StatuesRecord> DELETE_STATUS__DELETE_STATUS_STATUS_ID_FKEY = Internal.createForeignKey(DeleteStatus.DELETE_STATUS, DSL.name("delete_status_status_id_fkey"), new TableField[] { DeleteStatus.DELETE_STATUS.STATUS_ID }, Keys.STATUES_PKEY, new TableField[] { Statues.STATUES.STATUS_ID }, true);
    public static final ForeignKey<GroupEntryQualificationsRecord, GroupsRecord> GROUP_ENTRY_QUALIFICATIONS__GROUP_ENTRY_QUALIFICATIONS_GROUP_ID_FKEY = Internal.createForeignKey(GroupEntryQualifications.GROUP_ENTRY_QUALIFICATIONS, DSL.name("group_entry_qualifications_group_id_fkey"), new TableField[] { GroupEntryQualifications.GROUP_ENTRY_QUALIFICATIONS.GROUP_ID }, Keys.GROUPS_PKEY, new TableField[] { Groups.GROUPS.GROUP_ID }, true);
    public static final ForeignKey<InviteGroupRecord, GroupsRecord> INVITE_GROUP__INVITE_GROUP_GROUP_ID_FKEY = Internal.createForeignKey(InviteGroup.INVITE_GROUP, DSL.name("invite_group_group_id_fkey"), new TableField[] { InviteGroup.INVITE_GROUP.GROUP_ID }, Keys.GROUPS_PKEY, new TableField[] { Groups.GROUPS.GROUP_ID }, true);
    public static final ForeignKey<ItemsRecord, CategoriesRecord> ITEMS__ITEMS_CATEGORY_ID_FKEY = Internal.createForeignKey(Items.ITEMS, DSL.name("items_category_id_fkey"), new TableField[] { Items.ITEMS.CATEGORY_ID }, Keys.CATEGORIES_PKEY, new TableField[] { Categories.CATEGORIES.CATEGORY_ID }, true);
    public static final ForeignKey<StatuesRecord, CategoriesRecord> STATUES__STATUES_CATEGORY_ID_FKEY = Internal.createForeignKey(Statues.STATUES, DSL.name("statues_category_id_fkey"), new TableField[] { Statues.STATUES.CATEGORY_ID }, Keys.CATEGORIES_PKEY, new TableField[] { Categories.CATEGORIES.CATEGORY_ID }, true);
    public static final ForeignKey<UserGroupsRecord, GroupsRecord> USER_GROUPS__USER_GROUPS_GROUP_ID_FKEY = Internal.createForeignKey(UserGroups.USER_GROUPS, DSL.name("user_groups_group_id_fkey"), new TableField[] { UserGroups.USER_GROUPS.GROUP_ID }, Keys.GROUPS_PKEY, new TableField[] { Groups.GROUPS.GROUP_ID }, true);
}
