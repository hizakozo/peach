/*
 * This file is generated by jOOQ.
 */
package com.example.peachapi.driver.peachdb.gen;


import com.example.peachapi.driver.peachdb.gen.tables.AssignedStatus;
import com.example.peachapi.driver.peachdb.gen.tables.Categories;
import com.example.peachapi.driver.peachdb.gen.tables.Databasechangelog;
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

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.assigned_status</code>.
     */
    public final AssignedStatus ASSIGNED_STATUS = AssignedStatus.ASSIGNED_STATUS;

    /**
     * The table <code>public.categories</code>.
     */
    public final Categories CATEGORIES = Categories.CATEGORIES;

    /**
     * The table <code>public.databasechangelog</code>.
     */
    public final Databasechangelog DATABASECHANGELOG = Databasechangelog.DATABASECHANGELOG;

    /**
     * The table <code>public.databasechangeloglock</code>.
     */
    public final Databasechangeloglock DATABASECHANGELOGLOCK = Databasechangeloglock.DATABASECHANGELOGLOCK;

    /**
     * The table <code>public.delete_category</code>.
     */
    public final DeleteCategory DELETE_CATEGORY = DeleteCategory.DELETE_CATEGORY;

    /**
     * The table <code>public.delete_group</code>.
     */
    public final DeleteGroup DELETE_GROUP = DeleteGroup.DELETE_GROUP;

    /**
     * The table <code>public.delete_item</code>.
     */
    public final DeleteItem DELETE_ITEM = DeleteItem.DELETE_ITEM;

    /**
     * The table <code>public.delete_status</code>.
     */
    public final DeleteStatus DELETE_STATUS = DeleteStatus.DELETE_STATUS;

    /**
     * The table <code>public.group_entry_qualifications</code>.
     */
    public final GroupEntryQualifications GROUP_ENTRY_QUALIFICATIONS = GroupEntryQualifications.GROUP_ENTRY_QUALIFICATIONS;

    /**
     * The table <code>public.groups</code>.
     */
    public final Groups GROUPS = Groups.GROUPS;

    /**
     * The table <code>public.invite_group</code>.
     */
    public final InviteGroup INVITE_GROUP = InviteGroup.INVITE_GROUP;

    /**
     * The table <code>public.items</code>.
     */
    public final Items ITEMS = Items.ITEMS;

    /**
     * The table <code>public.statues</code>.
     */
    public final Statues STATUES = Statues.STATUES;

    /**
     * The table <code>public.user_groups</code>.
     */
    public final UserGroups USER_GROUPS = UserGroups.USER_GROUPS;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            AssignedStatus.ASSIGNED_STATUS,
            Categories.CATEGORIES,
            Databasechangelog.DATABASECHANGELOG,
            Databasechangeloglock.DATABASECHANGELOGLOCK,
            DeleteCategory.DELETE_CATEGORY,
            DeleteGroup.DELETE_GROUP,
            DeleteItem.DELETE_ITEM,
            DeleteStatus.DELETE_STATUS,
            GroupEntryQualifications.GROUP_ENTRY_QUALIFICATIONS,
            Groups.GROUPS,
            InviteGroup.INVITE_GROUP,
            Items.ITEMS,
            Statues.STATUES,
            UserGroups.USER_GROUPS);
    }
}
