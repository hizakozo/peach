/*
 * This file is generated by jOOQ.
 */
package com.example.peachapi.driver.peachdb.gen.tables;


import com.example.peachapi.driver.peachdb.gen.Keys;
import com.example.peachapi.driver.peachdb.gen.Public;
import com.example.peachapi.driver.peachdb.gen.tables.records.UserGroupsRecord;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserGroups extends TableImpl<UserGroupsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.user_groups</code>
     */
    public static final UserGroups USER_GROUPS = new UserGroups();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserGroupsRecord> getRecordType() {
        return UserGroupsRecord.class;
    }

    /**
     * The column <code>public.user_groups.group_id</code>.
     */
    public final TableField<UserGroupsRecord, UUID> GROUP_ID = createField(DSL.name("group_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.user_groups.user_id</code>.
     */
    public final TableField<UserGroupsRecord, String> USER_ID = createField(DSL.name("user_id"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    private UserGroups(Name alias, Table<UserGroupsRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserGroups(Name alias, Table<UserGroupsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.user_groups</code> table reference
     */
    public UserGroups(String alias) {
        this(DSL.name(alias), USER_GROUPS);
    }

    /**
     * Create an aliased <code>public.user_groups</code> table reference
     */
    public UserGroups(Name alias) {
        this(alias, USER_GROUPS);
    }

    /**
     * Create a <code>public.user_groups</code> table reference
     */
    public UserGroups() {
        this(DSL.name("user_groups"), null);
    }

    public <O extends Record> UserGroups(Table<O> child, ForeignKey<O, UserGroupsRecord> key) {
        super(child, key, USER_GROUPS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<UserGroupsRecord> getPrimaryKey() {
        return Keys.USER_GROUPS_PKEY;
    }

    @Override
    public List<UniqueKey<UserGroupsRecord>> getKeys() {
        return Arrays.<UniqueKey<UserGroupsRecord>>asList(Keys.USER_GROUPS_PKEY);
    }

    @Override
    public List<ForeignKey<UserGroupsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UserGroupsRecord, ?>>asList(Keys.USER_GROUPS__USER_GROUPS_GROUP_ID_FKEY);
    }

    public Groups groups() {
        return new Groups(this, Keys.USER_GROUPS__USER_GROUPS_GROUP_ID_FKEY);
    }

    @Override
    public UserGroups as(String alias) {
        return new UserGroups(DSL.name(alias), this);
    }

    @Override
    public UserGroups as(Name alias) {
        return new UserGroups(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserGroups rename(String name) {
        return new UserGroups(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserGroups rename(Name name) {
        return new UserGroups(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<UUID, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
