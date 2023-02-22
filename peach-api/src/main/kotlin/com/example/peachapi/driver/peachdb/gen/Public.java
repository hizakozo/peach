/*
 * This file is generated by jOOQ.
 */
package com.example.peachapi.driver.peachdb.gen;


import com.example.peachapi.driver.peachdb.gen.tables.Categories;
import com.example.peachapi.driver.peachdb.gen.tables.Databasechangelog;
import com.example.peachapi.driver.peachdb.gen.tables.Databasechangeloglock;
import com.example.peachapi.driver.peachdb.gen.tables.GoogleAuthentications;
import com.example.peachapi.driver.peachdb.gen.tables.GroupEntryQualifications;
import com.example.peachapi.driver.peachdb.gen.tables.Groups;
import com.example.peachapi.driver.peachdb.gen.tables.ItemStatues;
import com.example.peachapi.driver.peachdb.gen.tables.Items;
import com.example.peachapi.driver.peachdb.gen.tables.Statues;
import com.example.peachapi.driver.peachdb.gen.tables.UserGroups;
import com.example.peachapi.driver.peachdb.gen.tables.Users;

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
     * The table <code>public.google_authentications</code>.
     */
    public final GoogleAuthentications GOOGLE_AUTHENTICATIONS = GoogleAuthentications.GOOGLE_AUTHENTICATIONS;

    /**
     * The table <code>public.group_entry_qualifications</code>.
     */
    public final GroupEntryQualifications GROUP_ENTRY_QUALIFICATIONS = GroupEntryQualifications.GROUP_ENTRY_QUALIFICATIONS;

    /**
     * The table <code>public.groups</code>.
     */
    public final Groups GROUPS = Groups.GROUPS;

    /**
     * The table <code>public.item_statues</code>.
     */
    public final ItemStatues ITEM_STATUES = ItemStatues.ITEM_STATUES;

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
     * The table <code>public.users</code>.
     */
    public final Users USERS = Users.USERS;

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
            Categories.CATEGORIES,
            Databasechangelog.DATABASECHANGELOG,
            Databasechangeloglock.DATABASECHANGELOGLOCK,
            GoogleAuthentications.GOOGLE_AUTHENTICATIONS,
            GroupEntryQualifications.GROUP_ENTRY_QUALIFICATIONS,
            Groups.GROUPS,
            ItemStatues.ITEM_STATUES,
            Items.ITEMS,
            Statues.STATUES,
            UserGroups.USER_GROUPS,
            Users.USERS);
    }
}
